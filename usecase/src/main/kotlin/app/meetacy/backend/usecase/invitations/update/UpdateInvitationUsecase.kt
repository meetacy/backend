package app.meetacy.backend.usecase.invitations.update

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.Invitation
import app.meetacy.backend.usecase.types.authorizeWithUserId

class UpdateInvitationUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository
) {
    sealed interface Result {
        object Unauthorized: Result
        object InvitationNotFound: Result
        object Success: Result
        object MeetingNotFound: Result
    }

    suspend fun update(
        id: InvitationId,
        token: AccessIdentity,
        title: String? = null,
        description: String? = null,
        expiryDate: Date? = null,
        meetingId: MeetingId? = null
    ): Result {
        val authorId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        val invitation = storage.getInvitationOrNull(id) ?: return Result.InvitationNotFound

        if (invitation.invitorUserId != authorId) return Result.InvitationNotFound
        if (meetingId != null) {
            if (storage.getMeetingOrNull(meetingId) == null || !ableToInvite(meetingId, authorId, invitation.invitedUserId))
                return Result.MeetingNotFound
        }
        storage.update(id, title, description, expiryDate, meetingId)
        return Result.Success
    }

    interface Storage {
        suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean
        suspend fun getInvitationOrNull(id: InvitationId): Invitation?
        suspend fun getMeetingOrNull(id: MeetingId): FullMeeting?
        suspend fun update(
            invitationId: InvitationId,
            title: String? = null,
            description: String? = null,
            expiryDate: Date? = null,
            meetingId: MeetingId? = null
        ): Boolean
    }

    private suspend fun ableToInvite(meetingId: MeetingId, invitorId: UserId, invitedId: UserId): Boolean =
        storage.isParticipating(meetingId, invitorId) && storage.isParticipating(meetingId, invitedId)
}
