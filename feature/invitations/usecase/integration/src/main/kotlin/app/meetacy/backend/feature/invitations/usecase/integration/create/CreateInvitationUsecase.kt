package app.meetacy.backend.feature.invitations.usecase.integration.create

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsStorage
import app.meetacy.backend.feature.invitations.database.types.DatabaseInvitation
import app.meetacy.backend.feature.invitations.usecase.create.CreateInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.integration.types.mapToUsecase
import app.meetacy.backend.feature.invitations.usecase.types.GetInvitationsViewsRepository
import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.notifications.usecase.add.AddNotificationUsecase
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.invitation.FullInvitation
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.createInvitation() {
    val createInvitationUsecase by singleton<CreateInvitationUsecase> {
        val authRepository: AuthRepository by getting
        val accessHashGenerator: AccessHashGenerator by getting
        val invitationsRepository: GetInvitationsViewsRepository by getting
        val getUsersViewsRepository: GetUsersViewsRepository by getting
        val storage = object : CreateInvitationUsecase.Storage {
            private val friendsStorage: FriendsStorage by getting
            private val invitationTable: InvitationsStorage by getting
            private val meetingsStorage: MeetingsStorage by getting
            private val addNotificationUsecase: AddNotificationUsecase by getting

            override suspend fun isSubscribers(subscribersIds: List<UserId>, userId: UserId): List<Boolean> {
                val users = subscribersIds.map { subscriberId ->
                    FriendsStorage.IsSubscriber(
                        userId = userId,
                        subscriberId = subscriberId
                    )
                }
                return friendsStorage.isSubscribers(users)
            }

            override suspend fun getMeeting(meetingId: MeetingId): FullMeeting? =
                meetingsStorage.getMeetingOrNull(meetingId)

            override suspend fun getInvitations(inviterId: UserId, meetingId: MeetingId): List<FullInvitation> =
                invitationTable.getInvitations(inviterId, meetingId).map(DatabaseInvitation::mapToUsecase)

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
        CreateInvitationUsecase(
            authRepository = authRepository,
            storage = storage,
            hashGenerator = accessHashGenerator,
            invitationsRepository = invitationsRepository,
            getUsersUsersViewsRepository = getUsersViewsRepository
        )
    }
}
