package app.meetacy.backend.usecase.invitations.create

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*

class CreateInvitationUsecase (
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val hashGenerator: HashGenerator
) {
    sealed interface Result {
        data class Success(val invitation: InvitationId): Result
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
        meetingId: MeetingId,
        invitedUserId: UserId
    ): Result {
        val invitorId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        storage.getMeeting(meetingId) ?: return Result.MeetingNotFound
        storage.getUser(invitedUserId) ?: return Result.UserNotFound

        when {
            !storage.isSubscriberOf(invitedUserId, invitorId) -> return Result.NoPermissions
            (expiryDate < DateTime.now()) -> return Result.InvalidExpiryDate
            storage.getInvitationsFrom(invitorId)
                .any { it.invitedUserId == invitedUserId && it.meeting == meetingId }
            -> return Result.UserAlreadyInvited

            else -> return Result.Success(
                invitation = storage.createInvitation(
                    AccessHash(hashGenerator.generate()),
                    invitedUserId,
                    invitorId,
                    expiryDate,
                    meetingId
                )
            )
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
