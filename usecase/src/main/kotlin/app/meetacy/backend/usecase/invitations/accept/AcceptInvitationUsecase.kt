package app.meetacy.backend.usecase.invitations.accept

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId

class AcceptInvitationUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {
    sealed interface Result {
        object Success: Result
        object NotFound: Result
        object Unauthorized: Result
        object InvitationExpired: Result
        object MeetingNotFound: Result
    }

    suspend fun AccessIdentity.addToMeetingByInvitation(invitationId: InvitationId): Result {
        val userId = authRepository.authorizeWithUserId(this) { return Result.Unauthorized }

        return when {
            !storage.isInvited(userId, invitationId) -> Result.NotFound
            storage.isExpired(invitationId) -> Result.InvitationExpired
            !storage.doesMeetingExist(invitationId) -> Result.MeetingNotFound
            else -> {
                if (storage.addToMeetingByInvitation(userId, invitationId)) Result.Success else Result.NotFound
            }
        }
    }

    interface Storage {
        suspend fun isInvited(userId: UserId, invitationId: InvitationId): Boolean
        suspend fun addToMeetingByInvitation(userId: UserId, invitationId: InvitationId): Boolean
        suspend fun isExpired(id: InvitationId): Boolean
        suspend fun doesMeetingExist(id: InvitationId): Boolean
    }
}
