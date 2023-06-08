@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.invitations

import app.meetacy.backend.database.invitations.InvitationsTable.ACCESS_HASH
import app.meetacy.backend.database.invitations.InvitationsTable.EXPIRY_DATE
import app.meetacy.backend.database.invitations.InvitationsTable.INVITATION_ID
import app.meetacy.backend.database.invitations.InvitationsTable.INVITED_USER_ID
import app.meetacy.backend.database.invitations.InvitationsTable.INVITOR_USER_ID
import app.meetacy.backend.database.invitations.InvitationsTable.IS_ACCEPTED
import app.meetacy.backend.database.invitations.InvitationsTable.MEETING_ID
import app.meetacy.backend.database.types.DatabaseInvitation
import app.meetacy.backend.types.DATE_TIME_MAX_LIMIT
import app.meetacy.backend.types.HASH_LENGTH
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.invitation.InvitationIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object InvitationsTable : Table() {
    val INVITATION_ID = long("INVITATION_ID").autoIncrement()
    val EXPIRY_DATE = varchar("DATE", length = DATE_TIME_MAX_LIMIT)
    val INVITED_USER_ID = long("INVITED_USER_ID")
    val INVITOR_USER_ID = long("INVITOR_USER_ID")
    val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    val MEETING_ID = long("MEETING_ID")
    val IS_ACCEPTED = bool("IS_ACCEPTED").nullable().default(null)

    override val primaryKey = PrimaryKey(INVITATION_ID)
}

class InvitationsStorage(private val db: Database) {

    suspend fun addInvitation(
        accessHash: AccessHash,
        invitorUserId: UserId,
        invitedUserId: UserId,
        expiryDate: DateTime,
        meetingId: MeetingId
    ): InvitationId =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val invitationId = InvitationsTable.insert { statement ->
                statement[ACCESS_HASH] = accessHash.string
                statement[EXPIRY_DATE] = expiryDate.iso8601
                statement[INVITED_USER_ID] = invitedUserId.long
                statement[INVITOR_USER_ID] = invitorUserId.long
                statement[IS_ACCEPTED] = null
                statement[MEETING_ID] = meetingId.long
            }[INVITATION_ID]
            return@newSuspendedTransaction InvitationId(invitationId)
        }

    suspend fun getInvitationsByInvitationIds(list: List<InvitationId>): List<DatabaseInvitation> =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val rawInvitationIds = list.map { it.long }

            return@newSuspendedTransaction InvitationsTable.select {
                (INVITATION_ID inList rawInvitationIds)
            }
                .map { it.toInvitation() }
        }

    suspend fun getInvitations(userIds: List<UserId>): List<DatabaseInvitation> =
        newSuspendedTransaction(Dispatchers.IO, db)  {
            val rawUserIds = userIds.map { it.long }
            InvitationsTable.select { (INVITOR_USER_ID inList rawUserIds) or (INVITED_USER_ID inList rawUserIds) }
                .map { it.toInvitation() }
        }

    suspend fun markAsAccepted(
        invitationId: InvitationId
    ): Boolean = newSuspendedTransaction(Dispatchers.IO, db) {
        InvitationsTable.update(where = { (INVITATION_ID eq invitationId.long) }) {
            it[IS_ACCEPTED] = true
        } > 0
    }

    suspend fun markAsDenied(
        invitationId: InvitationId
    ): Boolean = newSuspendedTransaction(Dispatchers.IO, db) {
        InvitationsTable.update(where = { INVITATION_ID eq invitationId.long }) {
            it[IS_ACCEPTED] = false
        } > 0
    }

    suspend fun update(
        invitationId: InvitationId,
        expiryDate: DateTime? = null,
        meetingId: MeetingId? = null
    ): Boolean = newSuspendedTransaction(Dispatchers.IO, db) {
        val prevInvitation = getInvitationsByInvitationIds(
            listOf(invitationId)
        ).singleOrNull() ?: return@newSuspendedTransaction false

        InvitationsTable.update(where = { INVITATION_ID eq invitationId.long }) {
            it[EXPIRY_DATE] = expiryDate?.iso8601 ?: prevInvitation.expiryDate.iso8601
            it[MEETING_ID] = meetingId?.long ?: prevInvitation.meeting.long
        } > 0
    }

    suspend fun cancel(invitationId: InvitationId): Boolean = newSuspendedTransaction(Dispatchers.IO, db) {
        InvitationsTable.deleteWhere { INVITATION_ID eq invitationId.long } > 0
    }

    private fun ResultRow.toInvitation() = DatabaseInvitation(
        identity = InvitationIdentity(
            accessHash = AccessHash(this[ACCESS_HASH]),
            invitationId = InvitationId(this[INVITATION_ID])
        ),
        invitedUserId = UserId(this[INVITED_USER_ID]),
        invitorUserId = UserId(this[INVITOR_USER_ID]),
        meeting = MeetingId(this[MEETING_ID]),
        expiryDate = DateTime.parse(this[EXPIRY_DATE]),
        isAccepted = this[IS_ACCEPTED]
    )
}
