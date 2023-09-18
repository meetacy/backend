@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.feature.invitations.database.invitations

import app.meetacy.backend.constants.HASH_LENGTH
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsTable.INVITATION_ID
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsTable.INVITED_USER_ID
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsTable.INVITER_USER_ID
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsTable.IS_ACCEPTED
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsTable.MEETING_ID
import app.meetacy.backend.feature.meetings.database.meetings.MeetingsTable
import app.meetacy.backend.feature.invitations.database.types.DatabaseInvitation
import app.meetacy.backend.feature.users.database.users.UsersTable
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object InvitationsTable : Table() {
    val INVITATION_ID = long("INVITATION_ID").autoIncrement()
    val INVITED_USER_ID = reference("INVITED_USER_ID", UsersTable.USER_ID)
    val INVITER_USER_ID = reference("INVITER_USER_ID", UsersTable.USER_ID)
    val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    val MEETING_ID = reference("MEETING_ID", MeetingsTable.MEETING_ID)
    val IS_ACCEPTED = bool("IS_ACCEPTED").nullable().default(null)

    override val primaryKey = PrimaryKey(INVITATION_ID)
}

class InvitationsStorage(private val db: Database) {

    suspend fun addInvitation(
        accessHash: AccessHash,
        inviterUserId: UserId,
        invitedUserId: UserId,
        meetingId: MeetingId
    ): InvitationId =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val invitationId = InvitationsTable.insert { statement ->
                statement[ACCESS_HASH] = accessHash.string
                statement[INVITED_USER_ID] = invitedUserId.long
                statement[INVITER_USER_ID] = inviterUserId.long
                statement[IS_ACCEPTED] = null
                statement[MEETING_ID] = meetingId.long
            }[INVITATION_ID]
            return@newSuspendedTransaction InvitationId(invitationId)
        }

    suspend fun getInvitationsOrNull(list: List<InvitationId>): List<DatabaseInvitation?> =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val rawInvitationIds = list.map { it.long }

            val invitations =  InvitationsTable.select {
                (INVITATION_ID inList rawInvitationIds)
            }.map { it.toInvitation() }
                .associateBy { it.id }

            list.map { invitation -> invitations[invitation] }
        }

    suspend fun getInvitationsFrom(userId: UserId): List<DatabaseInvitation> =
        newSuspendedTransaction(Dispatchers.IO, db)  {
            InvitationsTable.select { (INVITER_USER_ID eq userId.long) }
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

    suspend fun cancel(invitationId: InvitationId): Boolean = newSuspendedTransaction(Dispatchers.IO, db) {
        InvitationsTable.deleteWhere { INVITATION_ID eq invitationId.long } > 0
    }

    private fun ResultRow.toInvitation() = DatabaseInvitation(
        id = InvitationId(this[INVITATION_ID]),
        invitedUserId = UserId(this[INVITED_USER_ID]),
        inviterUserId = UserId(this[INVITER_USER_ID]),
        meetingId = MeetingId(this[MEETING_ID]),
        isAccepted = this[IS_ACCEPTED]
    )
}
