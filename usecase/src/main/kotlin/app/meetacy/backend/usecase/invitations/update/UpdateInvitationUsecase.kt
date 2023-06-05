package app.meetacy.backend.usecase.invitations.update

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.invitation.InvitationIdentity
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*

class UpdateInvitationUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val getInvitationsViewsRepository: GetInvitationsViewsRepository
) {
    sealed interface Result {
        object Unauthorized: Result
        object InvitationNotFound: Result
        object MeetingNotFound: Result
        data class Success(val invitation: InvitationView): Result
    }

    suspend fun update(
        invitationIdentity: InvitationIdentity,
        token: AccessIdentity,
        expiryDate: DateTime? = null,
        meetingIdentity: MeetingIdentity? = null
    ): Result {
        val authorId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        val invitation = storage.getInvitationOrNull(invitationIdentity.id)
            ?.apply { require(identity == invitationIdentity) } ?: return Result.InvitationNotFound

        if (invitation.invitorUserId != authorId) return Result.InvitationNotFound
        if (meetingIdentity != null) {
            if (!ableToInvite(meetingIdentity.id, authorId, invitation.invitedUserId)
                || storage.getMeetingOrNull(meetingIdentity.id)?.identity != meetingIdentity) {
                    return Result.MeetingNotFound
                }
        }
        if (expiryDate != null && expiryDate <= DateTime.now())
        storage.update(invitationIdentity.id, expiryDate, meetingIdentity?.id)
        return Result.Success(invitation = getInvitationsViewsRepository.getInvitationView(authorId, invitation.id))
    }

    interface Storage {
        suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean
        suspend fun getInvitationOrNull(id: InvitationId): FullInvitation?
        suspend fun getMeetingOrNull(id: MeetingId): FullMeeting?
        suspend fun update(
            invitationId: InvitationId,
            expiryDate: DateTime? = null,
            meetingId: MeetingId? = null
        ): Boolean
    }

    private suspend fun ableToInvite(meetingId: MeetingId, invitorId: UserId, invitedId: UserId): Boolean =
        storage.isParticipating(meetingId, invitorId) && !storage.isParticipating(meetingId, invitedId)
}
