@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.invitations

import app.meetacy.backend.database.types.DatabaseInvitation
import app.meetacy.backend.types.DATE_MAX_LIMIT
import app.meetacy.backend.types.DESCRIPTION_MAX_LIMIT
import app.meetacy.backend.types.HASH_LENGTH
import app.meetacy.backend.types.TITLE_MAX_LIMIT
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.invitation.InvitationIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class InvitationsTable(private val db: Database) : Table() {
    private val INVITATION_ID = long("INVITATION_ID").autoIncrement()
    private val EXPIRY_DATE = varchar("DATE", length = DATE_MAX_LIMIT)
    private val INVITED_USER_ID = long("INVITED_USER_ID")
    private val INVITOR_USER_ID = long("INVITOR_USER_ID")
    private val DESCRIPTION = varchar("DESCRIPTION", length = DESCRIPTION_MAX_LIMIT)
    private val TITLE = varchar("TITLE", length = TITLE_MAX_LIMIT)
    private val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    private val MEETING_ID = long("MEETING_ID")

    override val primaryKey = PrimaryKey(INVITATION_ID)

    init {
        transaction(db) {
            SchemaUtils.create(this@InvitationsTable)
        }
    }

    suspend fun addInvitation(
        accessHash: AccessHash,
        expiryDate: Date,
        invitedUserId: UserId,
        invitorUserId: UserId,
        description: String,
        title: String
    ): InvitationId =
        newSuspendedTransaction(db = db) {
            val invitationId = insert { statement ->
                statement[ACCESS_HASH] = accessHash.string
                statement[EXPIRY_DATE] = expiryDate.iso8601
                statement[INVITED_USER_ID] = invitedUserId.long
                statement[INVITOR_USER_ID] = invitorUserId.long
                statement[DESCRIPTION] = description
                statement[TITLE] = title
            }[INVITATION_ID]
            return@newSuspendedTransaction InvitationId(invitationId)
        }

    suspend fun getInvitations(invitationIdsList: List<InvitationId>): List<DatabaseInvitation> =
        newSuspendedTransaction(db = db) {
            val rawInvitationIds = invitationIdsList.map { it.long }

            return@newSuspendedTransaction select { INVITATION_ID inList rawInvitationIds }
                .map { it.toInvitation() }
        }

    private fun ResultRow.toInvitation() = DatabaseInvitation(
        identity = InvitationIdentity(
            accessHash = AccessHash(this[ACCESS_HASH]),
            invitationId = InvitationId(this[INVITATION_ID])
        ),
        title = this[TITLE],
        description = this[DESCRIPTION],
        invitedUserId = UserId(this[INVITED_USER_ID]),
        invitorUserId = UserId(this[INVITOR_USER_ID]),
        meeting = MeetingId(this[MEETING_ID]),
        expiryDate = Date.parse(this[EXPIRY_DATE])
    )
}