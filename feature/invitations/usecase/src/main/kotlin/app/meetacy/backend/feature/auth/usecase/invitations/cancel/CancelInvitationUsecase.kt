package app.meetacy.backend.feature.auth.usecase.invitations.cancel

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.feature.auth.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.feature.auth.usecase.types.authorizeWithUserId

class CancelInvitationUsecase(
    private val authRepository: app.meetacy.backend.feature.auth.usecase.types.AuthRepository,
    private val storage: Storage
) {
    sealed interface Result {
        object Success: Result
        object Unauthorized: Result
        object NotFound: Result
    }

    suspend fun cancel(token: AccessIdentity, invitationId: InvitationId): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        val invitation = storage.getInvitation(invitationId) ?: return Result.NotFound

        if (invitation.isAccepted != null || invitation.inviterUserId != userId) return Result.NotFound

        storage.cancel(invitationId)
        return Result.Success
    }

    interface Storage {
        suspend fun cancel(id: InvitationId): Boolean
        suspend fun getInvitation(id: InvitationId): FullInvitation?
    }
}
