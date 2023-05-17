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
        with(storage) {
            if (!doesExist(id) || !isCreatedBy(authorId, id)) return Result.InvitationNotFound
            if (meetingId != null) {
                if (!doesExist(meetingId) || !ableToInvite(meetingId, authorId, getInvitedUser(id)))
                    return Result.MeetingNotFound
            }
            if (!update(id, title, description, expiryDate, meetingId)) return Result.InvitationNotFound
            return Result.Success
        }
    }

    interface Storage {
        suspend fun doesExist(invitationId: InvitationId): Boolean
        suspend fun getInvitedUser(invitationId: InvitationId): UserId
        suspend fun doesExist(meetingId: MeetingId): Boolean
        suspend fun ableToInvite(meetingId: MeetingId, invitorId: UserId, invitedId: UserId): Boolean
        suspend fun isCreatedBy(userId: UserId, invitationId: InvitationId): Boolean
        suspend fun update(
            invitationId: InvitationId,
            title: String? = null,
            description: String? = null,
            expiryDate: Date? = null,
            meetingId: MeetingId? = null
        ): Boolean
    }
}
