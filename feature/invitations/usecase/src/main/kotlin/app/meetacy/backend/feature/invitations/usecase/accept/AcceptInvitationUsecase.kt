package app.meetacy.backend.feature.invitations.usecase.accept

import app.meetacy.backend.types.invitation.FullInvitation
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId

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
        val meeting = storage.getMeetingOrNull(invitation.meetingId) ?: return Result.MeetingNotFound

        return when {
            invitation.invitedUserId != userId || storage.isParticipating(invitation.meetingId, userId) -> Result.NotFound
            else -> {
                storage.addParticipant(userId, invitation.meetingId, meeting.date)
                Result.Success
            }
        }
    }

    interface Storage {
        suspend fun getMeetingOrNull(id: MeetingId): FullMeeting?
        suspend fun getInvitationOrNull(id: InvitationId): FullInvitation?
        suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean
        suspend fun markAsAccepted(id: InvitationId)
        suspend fun addParticipant(participantId: UserId, meetingId: MeetingId, meetingDate: Date)
    }
}
