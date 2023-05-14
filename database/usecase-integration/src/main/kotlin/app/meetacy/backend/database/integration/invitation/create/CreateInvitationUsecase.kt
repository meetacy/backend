package app.meetacy.backend.database.integration.invitation.create

import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseCreateInvitationStorage(db: Database): CreateInvitationUsecase.Storage {
    private val friendsTable = FriendsTable(db)
    private val invitationTable = InvitationsTable(db)
    private val meetingsTable = MeetingsTable(db)
    private val usersTable = UsersTable(db)

    override suspend fun UserId.isFriend(user: UserId): Boolean =
        with(friendsTable) { isSubscribed(this@isFriend, user) && isSubscribed(user, this@isFriend) }

    override suspend fun UserId.isSubscriberOf(subscriberId: UserId): Boolean =
        friendsTable.isSubscribed(this, subscriberId)

    override suspend fun MeetingId.doesExist(): Boolean =
        meetingsTable.getMeetingOrNull(this) != null

    override suspend fun UserId.doesExist(): Boolean {
        val usersList = usersTable.getUsersOrNull(listOf(this))
        val user = if (usersList.isEmpty()) null else usersList.first()
        return user != null
    }

    override suspend fun createInvitation(
        accessHash: AccessHash,
        invitedUserId: UserId,
        invitorUserId: UserId,
        title: String,
        description: String,
        expiryDate: DateTime,
        meetingId: MeetingId
    ): InvitationId? {
        val invitationsList = invitationTable.getInvitations(
            invitedUserIdsList = listOf(invitedUserId),
            invitorUserId = invitorUserId
        )
        return if (invitationsList.any { it.meeting == meetingId }) {
            null
        } else {
            invitationTable.addInvitation(
                accessHash,
                title,
                description,
                invitorUserId,
                invitedUserId,
                expiryDate,
                meetingId
            )
        }
    }

}