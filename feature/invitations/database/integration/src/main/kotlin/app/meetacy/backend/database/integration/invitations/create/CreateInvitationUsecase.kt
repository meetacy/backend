package app.meetacy.backend.database.integration.invitations.create

import app.meetacy.backend.database.friends.FriendsStorage
import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.invitations.InvitationsStorage
import app.meetacy.backend.database.meetings.MeetingsStorage
import app.meetacy.backend.database.users.UsersStorage
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.usecase.notifications.add.AddNotificationUsecase
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.FullUser
import org.jetbrains.exposed.sql.Database

class DatabaseCreateInvitationStorage(
    db: Database,
    private val addNotificationUsecase: AddNotificationUsecase
): CreateInvitationUsecase.Storage {
    private val friendsStorage = FriendsStorage(db)
    private val invitationTable = InvitationsStorage(db)
    private val meetingsStorage = MeetingsStorage(db)
    private val usersStorage = UsersStorage(db)

    override suspend fun isSubscriberOf(subscriberId: UserId, authorId: UserId): Boolean =
        friendsStorage.isSubscribed(authorId, subscriberId)

    override suspend fun getMeeting(meetingId: MeetingId): FullMeeting? =
        meetingsStorage.getMeetingOrNull(meetingId)?.mapToUsecase()

    override suspend fun getUser(id: UserId): FullUser? =
        usersStorage.getUsersOrNull(listOf(id)).singleOrNull()?.mapToUsecase()

    override suspend fun getInvitationsFrom(authorId: UserId): List<FullInvitation> =
        invitationTable.getInvitationsFrom(authorId)
            .map { it.mapToUsecase() }

    override suspend fun createInvitation(
        accessHash: AccessHash,
        invitedUserId: UserId,
        inviterUserId: UserId,
        meetingId: MeetingId
    ): InvitationId =
        invitationTable.addInvitation(accessHash, inviterUserId, invitedUserId, meetingId)

    override suspend fun addNotification(userId: UserId, inviterId: UserId, meetingId: MeetingId) {
        addNotificationUsecase.addInvitation(
            userId = userId,
            inviterId = inviterId,
            meetingId = meetingId,
            date = DateTime.now()
        )
    }

}
