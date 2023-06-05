@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.invitations

import app.meetacy.backend.database.types.DatabaseInvitation
import app.meetacy.backend.types.DATE_TIME_MAX_LIMIT
import app.meetacy.backend.types.HASH_LENGTH
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.invitation.InvitationIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class InvitationsTable(private val db: Database) : Table() {
    private val INVITATION_ID = long("INVITATION_ID").autoIncrement()
    private val EXPIRY_DATE = varchar("DATE", length = DATE_TIME_MAX_LIMIT)
    private val INVITED_USER_ID = long("INVITED_USER_ID")
    private val INVITOR_USER_ID = long("INVITOR_USER_ID")
    private val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    private val MEETING_ID = long("MEETING_ID")
    private val IS_ACCEPTED = bool("IS_ACCEPTED").nullable().default(null)

    override val primaryKey = PrimaryKey(INVITATION_ID)

    init {
        transaction(db) {
            SchemaUtils.create(this@InvitationsTable)
        }
    }

    suspend fun addInvitation(
        accessHash: AccessHash,
        invitorUserId: UserId,
        invitedUserId: UserId,
        expiryDate: DateTime,
        meetingId: MeetingId
    ): InvitationId =
        newSuspendedTransaction(db = db) {
            val invitationId = insert { statement ->
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
        newSuspendedTransaction(db = db) {
            val rawInvitationIds = list.map { it.long }

            return@newSuspendedTransaction select {
                (INVITATION_ID inList rawInvitationIds)
            }
                .map { it.toInvitation() }
        }

    suspend fun getInvitations(userIds: List<UserId>): List<DatabaseInvitation> =
        newSuspendedTransaction(db = db)  {
            val rawUserIds = userIds.map { it.long }
            select { (INVITOR_USER_ID inList rawUserIds) or (INVITED_USER_ID inList rawUserIds) }
                .map { it.toInvitation() }
        }

    suspend fun markAsAccepted(
        invitationId: InvitationId
    ): Boolean = newSuspendedTransaction(db = db) {
        update(where = { (INVITATION_ID eq invitationId.long) }) {
            it[IS_ACCEPTED] = true
        } > 0
    }

    suspend fun markAsDenied(
        invitationId: InvitationId
    ): Boolean = newSuspendedTransaction(db = db) {
        update(where = { INVITATION_ID eq invitationId.long }) {
            it[IS_ACCEPTED] = false
        } > 0
    }

    suspend fun update(
        invitationId: InvitationId,
        expiryDate: DateTime? = null,
        meetingId: MeetingId? = null
    ): Boolean = newSuspendedTransaction(db = db) {
        val prevInvitation = getInvitationsByInvitationIds(
            listOf(invitationId)
        ).singleOrNull() ?: return@newSuspendedTransaction false

        update(where = { INVITATION_ID eq invitationId.long }) {
            it[EXPIRY_DATE] = expiryDate?.iso8601 ?: prevInvitation.expiryDate.iso8601
            it[MEETING_ID] = meetingId?.long ?: prevInvitation.meeting.long
        } > 0
    }

    suspend fun cancel(invitationId: InvitationId): Boolean = newSuspendedTransaction(db = db) {
        deleteWhere { INVITATION_ID eq invitationId.long } > 0
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
