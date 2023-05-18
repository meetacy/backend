package app.meetacy.backend.usecase.invitations.cancel

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.authorizeWithUserId

class CancelInvitationUsecase(
    val authRepository: AuthRepository,
    val storage: Storage
) {
    sealed interface Result {
        object Success: Result
        object Unauthorized: Result
        object NoPermissions: Result
        object NotFound: Result
    }

    suspend fun cancel(token: AccessIdentity, invitationId: InvitationId): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        val invitation = storage.getInvitation(invitationId) ?: return Result.NotFound

        if (invitation.isAccepted != null || invitation.expiryDate <= DateTime.now()) return Result.NotFound
        if (invitation.invitorUserId != userId) return Result.NoPermissions

        storage.cancel(invitationId)
        return Result.Success
    }

    interface Storage {
        suspend fun cancel(id: InvitationId): Boolean
        suspend fun getInvitation(id: InvitationId): FullInvitation?
    }
}