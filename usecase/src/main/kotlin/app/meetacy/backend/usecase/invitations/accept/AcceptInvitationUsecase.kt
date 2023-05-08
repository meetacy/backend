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
    }

    suspend fun AccessIdentity.isInvited(invitationId: InvitationId): Result {
        val userId = authRepository.authorizeWithUserId(this) { return Result.Unauthorized }

        with(storage) {
            if (userId.isInvited(invitationId)) {
                userId.addToMeetingByInvitation(invitationId)
            } else return Result.NotFound
        }

        return Result.Success
    }

    interface Storage {
        suspend fun UserId.isInvited(invitationId: InvitationId): Boolean
        suspend fun UserId.addToMeetingByInvitation(invitationId: InvitationId)
    }
}