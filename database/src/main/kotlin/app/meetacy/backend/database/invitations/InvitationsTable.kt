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
    private val IS_ACCEPTED = bool("IS_ACCEPTED").nullable().default(null)

    override val primaryKey = PrimaryKey(INVITATION_ID)

    init {
        transaction(db) {
            SchemaUtils.create(this@InvitationsTable)
        }
    }

    suspend fun addInvitation(
        accessHash: AccessHash,
        title: String,
        description: String,
        invitorUserId: UserId,
        invitedUserId: UserId,
        expiryDate: Date,
        meetingId: MeetingId
    ): InvitationId =
        newSuspendedTransaction(db = db) {
            val invitationId = insert { statement ->
                statement[ACCESS_HASH] = accessHash.string
                statement[EXPIRY_DATE] = expiryDate.iso8601
                statement[INVITED_USER_ID] = invitedUserId.long
                statement[INVITOR_USER_ID] = invitorUserId.long
                statement[DESCRIPTION] = description
                statement[TITLE] = title
                statement[IS_ACCEPTED] = null
                statement[MEETING_ID] = meetingId.long
            }[INVITATION_ID]
            return@newSuspendedTransaction InvitationId(invitationId)
        }

    /**
     * Returns list of invitations, which IDs are listed in [invitationIdsList]
     */
    suspend fun getInvitationsByInvitationIds(invitedUserId: UserId, invitationIdsList: List<InvitationId>): List<DatabaseInvitation> =
        newSuspendedTransaction(db = db) {
            val rawInvitationIds = invitationIdsList.map { it.long }

            return@newSuspendedTransaction select {
                (INVITATION_ID inList rawInvitationIds) and
                    (INVITED_USER_ID eq invitedUserId.long)
            }
                .map { it.toInvitation() }
        }

    suspend fun getInvitationsByInvitationIds(invitationIdsList: List<InvitationId>): List<DatabaseInvitation> =
        newSuspendedTransaction(db = db) {
            val rawInvitationIds = invitationIdsList.map { it.long }

            return@newSuspendedTransaction select {
                (INVITATION_ID inList rawInvitationIds)
            }
                .map { it.toInvitation() }
        }

    /**
     * Returns list of invitations sent by [invitedUserId], and [invitorUserIdsList] if specified
     */
    suspend fun getInvitations(
        invitorUserIdsList: List<UserId> = emptyList(),
        invitedUserId: UserId
    ): List<DatabaseInvitation> =
        newSuspendedTransaction(db = db) {
            return@newSuspendedTransaction if (invitorUserIdsList.isEmpty()) {
                select { INVITED_USER_ID eq invitedUserId.long }
                    .map { it.toInvitation() }
            } else {
                select {
                    (INVITED_USER_ID eq invitedUserId.long) and
                            (INVITOR_USER_ID inList invitorUserIdsList.map { it.long })
                }.map { it.toInvitation() }
            }
        }

    suspend fun getInvitations(
        invitorUserId: UserId,
        invitedUserIdsList: List<UserId> = emptyList()
    ): List<DatabaseInvitation> =
        newSuspendedTransaction(db = db) {
            return@newSuspendedTransaction if (invitedUserIdsList.isEmpty()) {
                select { INVITOR_USER_ID eq invitorUserId.long }
                    .map { it.toInvitation() }
            } else {
                select {
                    (INVITOR_USER_ID eq invitorUserId.long) and
                            (INVITED_USER_ID inList invitedUserIdsList.map { it.long })
                }
                    .map { it.toInvitation() }
            }
        }

    suspend fun markAsAccepted(
        userId: UserId,
        invitationId: InvitationId
    ): Boolean = newSuspendedTransaction(db = db) {
        getInvitationsByInvitationIds(invitedUserId = userId, invitationIdsList = listOf(invitationId))
            .singleOrNull() ?: return@newSuspendedTransaction false

        update(where = { (INVITATION_ID eq invitationId.long) and (INVITED_USER_ID eq userId.long) }) {
            it[IS_ACCEPTED] = true
        } > 0
    }

    suspend fun update(
        invitationId: InvitationId,
        invitorUserId: UserId,
        title: String? = null,
        description: String? = null,
        expiryDate: Date? = null,
        meetingId: MeetingId? = null
    ): Boolean = newSuspendedTransaction(db = db) {
        // get invitation, and return error if invitation not found
        val prevInvitation = getInvitationsByInvitationIds(
            listOf(invitationId)
        ).singleOrNull() ?: return@newSuspendedTransaction false

        update(where = { (INVITATION_ID eq invitationId.long) and (INVITOR_USER_ID eq invitorUserId.long) }) {
            it[TITLE] = title ?: prevInvitation.title
            it[DESCRIPTION] = description ?: prevInvitation.description
            it[EXPIRY_DATE] = expiryDate?.iso8601 ?: prevInvitation.expiryDate.iso8601
            it[MEETING_ID] = meetingId?.long ?: prevInvitation.meeting.long
        } > 0
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
        expiryDate = Date.parse(this[EXPIRY_DATE]),
        isAccepted = this[IS_ACCEPTED]
    )
}