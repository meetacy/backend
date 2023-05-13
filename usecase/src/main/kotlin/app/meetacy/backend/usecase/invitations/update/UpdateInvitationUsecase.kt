package app.meetacy.backend.usecase.invitations.update

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId

class UpdateInvitationUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository
) {
    sealed interface Result {
        object Unauthorized: Result
        object InvitationNotFound: Result
        object InvalidData: Result
        object Success: Result
        object MeetingNotFound: Result
    }

    suspend fun InvitationId.update(
        token: AccessIdentity,
        title: String? = null,
        description: String? = null,
        expiryDate: Date? = null,
        meetingId: MeetingId? = null
    ): Result {
        val authorId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        with(storage) {
            if (!doesExist() || !isCreatedBy(authorId)) return Result.InvitationNotFound
            if (meetingId != null) {
                if (!meetingId.doesExist() || !meetingId.ableToInvite(authorId, getInvitedUser()))
                    return Result.MeetingNotFound
            }
            if (listOf(title, description, expiryDate, meetingId).all { it == null }) return Result.InvalidData
            if (!update(title, description, expiryDate, meetingId)) return Result.InvalidData
            return Result.Success
        }
    }

    interface Storage {
        suspend fun InvitationId.doesExist(): Boolean
        suspend fun InvitationId.getInvitedUser(): UserId
        suspend fun MeetingId.doesExist(): Boolean
        suspend fun MeetingId.ableToInvite(invitorId: UserId, invitedId: UserId): Boolean
        suspend fun InvitationId.isCreatedBy(userId: UserId): Boolean
        suspend fun InvitationId.update(
            title: String? = null,
            description: String? = null,
            expiryDate: Date? = null,
            meetingId: MeetingId? = null
        ): Boolean
    }
}