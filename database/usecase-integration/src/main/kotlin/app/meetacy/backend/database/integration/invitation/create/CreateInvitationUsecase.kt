package app.meetacy.backend.database.integration.invitation.create

import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.FullUser
import org.jetbrains.exposed.sql.Database

class DatabaseCreateInvitationStorage(db: Database): CreateInvitationUsecase.Storage {
    private val friendsTable = FriendsTable(db)
    private val invitationTable = InvitationsTable(db)
    private val meetingsTable = MeetingsTable(db)
    private val usersTable = UsersTable(db)

    override suspend fun isSubscriberOf(subscriberId: UserId, authorId: UserId): Boolean =
        friendsTable.isSubscribed(authorId, subscriberId)

    override suspend fun getMeeting(meetingId: MeetingId): FullMeeting? =
        meetingsTable.getMeetingOrNull(meetingId)?.mapToUsecase()

    override suspend fun getUser(id: UserId): FullUser? =
        usersTable.getUsersOrNull(listOf(id)).singleOrNull()?.mapToUsecase()

    override suspend fun getInvitationsFrom(authorId: UserId): List<FullInvitation> =
        invitationTable.getInvitations(userIds = listOf(authorId))
            .filter { it.invitorUserId == authorId }
            .map { it.mapToUsecase() }

    override suspend fun createInvitation(
        accessHash: AccessHash,
        invitedUserId: UserId,
        invitorUserId: UserId,
        expiryDate: DateTime,
        meetingId: MeetingId
    ): InvitationId =
        invitationTable.addInvitation(accessHash, invitorUserId, invitedUserId, expiryDate, meetingId)

}
