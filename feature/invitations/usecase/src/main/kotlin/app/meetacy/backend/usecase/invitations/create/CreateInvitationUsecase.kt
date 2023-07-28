package app.meetacy.backend.usecase.invitations.create

import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.user.UserIdentity
import app.meetacy.backend.usecase.types.*

class CreateInvitationUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val hashGenerator: AccessHashGenerator,
    private val invitationsRepository: GetInvitationsViewsRepository
) {
    sealed interface Result {
        data class Success(val invitation: InvitationView): Result
        object Unauthorized: Result
        object NoPermissions: Result
        object UserNotFound: Result
        object MeetingNotFound: Result
        object UserAlreadyInvited: Result
    }

    suspend fun createInvitation(
        token: AccessIdentity,
        meetingIdentity: MeetingIdentity,
        userIdentity: UserIdentity
    ): Result {
        val inviterId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }

        storage.getMeeting(meetingIdentity.id)
            ?.apply { require(identity == meetingIdentity) } ?: return Result.MeetingNotFound
        storage.getUser(userIdentity.id)
            ?.apply { require(identity == userIdentity) } ?: return Result.UserNotFound

        when {
            !storage.isSubscriberOf(userIdentity.id, inviterId) -> return Result.NoPermissions
            storage.getInvitationsFrom(inviterId)
                .any { it.invitedUserId == userIdentity.id && it.meetingId == meetingIdentity.id }
            -> return Result.UserAlreadyInvited

            else -> {
                val id = storage.createInvitation(
                    AccessHash(hashGenerator.generate()),
                    userIdentity.id,
                    inviterId,
                    meetingIdentity.id
                )
                storage.addNotification(
                    userId = userIdentity.id,
                    inviterId = inviterId,
                    meetingId = meetingIdentity.id
                )

                return Result.Success(invitation = invitationsRepository.getInvitationView(inviterId, id))
            }
        }
    }

    interface Storage {
        suspend fun isSubscriberOf(subscriberId: UserId, authorId: UserId): Boolean
        suspend fun getMeeting(meetingId: MeetingId): FullMeeting?
        suspend fun getUser(id: UserId): FullUser?
        suspend fun getInvitationsFrom(authorId: UserId): List<FullInvitation>
        suspend fun createInvitation(
            accessHash: AccessHash,
            invitedUserId: UserId,
            inviterUserId: UserId,
            meetingId: MeetingId
        ): InvitationId
        suspend fun addNotification(
            userId: UserId,
            inviterId: UserId,
            meetingId: MeetingId
        )
    }
}
