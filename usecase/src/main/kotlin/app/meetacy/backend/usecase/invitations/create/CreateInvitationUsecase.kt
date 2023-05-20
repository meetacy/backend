package app.meetacy.backend.usecase.invitations.create

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.user.UserIdentity
import app.meetacy.backend.usecase.types.*

class CreateInvitationUsecase (
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val hashGenerator: HashGenerator,
    private val getInvitationsViewsRepository: GetInvitationsViewsRepository
) {
    sealed interface Result {
        data class Success(val invitation: InvitationView): Result
        object Unauthorized: Result
        object NoPermissions: Result
        object UserNotFound: Result
        object MeetingNotFound: Result
        object UserAlreadyInvited: Result
        object InvalidExpiryDate : Result
    }

    suspend fun createInvitation(
        token: AccessIdentity,
        expiryDate: DateTime,
        meetingIdentity: MeetingIdentity,
        invitedUserIdentity: UserIdentity
    ): Result {
        val invitorId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        storage.getMeeting(meetingIdentity.id)
            ?.apply { require(identity == meetingIdentity) } ?: return Result.MeetingNotFound
        storage.getUser(invitedUserIdentity.id)
            ?.apply { require(identity == invitedUserIdentity) } ?: return Result.UserNotFound

        when {
            !storage.isSubscriberOf(invitedUserIdentity.id, invitorId) -> return Result.NoPermissions
            (expiryDate < DateTime.now()) -> return Result.InvalidExpiryDate
            storage.getInvitationsFrom(invitorId)
                .any { it.invitedUserId == invitedUserIdentity.id && it.meeting == meetingIdentity.id }
            -> return Result.UserAlreadyInvited

            else -> {
                val id = storage.createInvitation(
                    AccessHash(hashGenerator.generate()),
                    invitedUserIdentity.id,
                    invitorId,
                    expiryDate,
                    meetingIdentity.id
                )

                return Result.Success(invitation = getInvitationsViewsRepository.getInvitationView(invitorId, id))
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
            invitorUserId: UserId,
            expiryDate: DateTime,
            meetingId: MeetingId
        ): InvitationId
    }
}
