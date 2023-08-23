package app.meetacy.backend.feature.invitations.usecase.integration

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsStorage
import app.meetacy.backend.feature.invitations.usecase.create.CreateInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.integration.types.mapToUsecase
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation
import app.meetacy.backend.feature.invitations.usecase.types.GetInvitationsViewsRepository
import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.notifications.usecase.notifications.add.AddNotificationUsecase
import app.meetacy.backend.feature.users.database.integration.types.mapToUsecase
import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.FullUser
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder
import app.meetacy.feature.meetings.database.integration.types.mapToUsecase

internal fun DIBuilder.createInvitation() {
    val createInvitationUsecase by singleton<CreateInvitationUsecase> {
        val authRepository: AuthRepository by getting
        val hashGenerator: AccessHashGenerator by getting
        val invitationsRepository: GetInvitationsViewsRepository by getting
        val storage = object : CreateInvitationUsecase.Storage {
            private val friendsStorage: FriendsStorage by getting
            private val invitationTable: InvitationsStorage by getting
            private val meetingsStorage: MeetingsStorage by getting
            private val usersStorage: UsersStorage by getting
            private val addNotificationUsecase: AddNotificationUsecase by getting

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
        CreateInvitationUsecase(authRepository, storage, hashGenerator, invitationsRepository)
    }
}
