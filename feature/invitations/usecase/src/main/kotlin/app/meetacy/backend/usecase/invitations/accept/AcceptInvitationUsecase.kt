package app.meetacy.backend.usecase.invitations.accept

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.types.auth.authorizeWithUserId

class AcceptInvitationUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {
    sealed interface Result {
        object Success: Result
        object NotFound: Result
        object Unauthorized: Result
        object MeetingNotFound: Result
    }

    suspend fun accept(token: AccessIdentity, invitationId: InvitationId): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        val invitation = storage.getInvitationOrNull(invitationId) ?: return Result.NotFound

        return when {
            invitation.invitedUserId != userId || storage.isParticipating(invitation.meetingId, userId) -> Result.NotFound
            storage.getMeetingOrNull(invitation.meetingId) == null -> Result.MeetingNotFound
            else -> {
                storage.addParticipant(invitation.meetingId, userId)
                Result.Success
            }
        }
    }

    interface Storage {
        suspend fun getMeetingOrNull(id: MeetingId): FullMeeting?
        suspend fun getInvitationOrNull(id: InvitationId): FullInvitation?
        suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean
        suspend fun markAsAccepted(id: InvitationId)
        suspend fun addParticipant(meetingId: MeetingId, userId: UserId)
    }
}
