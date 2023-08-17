package app.meetacy.backend.feature.invitations.usecase.deny

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation
import app.meetacy.backend.types.auth.authorizeWithUserId

class DenyInvitationUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {
    sealed interface Result {
        object Success: Result
        object Unauthorized: Result
        object NoPermissions: Result
        object NotFound: Result
    }

    suspend fun markAsDenied(token: AccessIdentity, invitationId: InvitationId): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        storage.getInvitation(invitationId)
            ?.apply { require(this.id == invitationId) } ?: return Result.NotFound

        if (!isInvited(invitationId, userId)) return Result.NoPermissions
        storage.markAsDenied(invitationId)

        return Result.Success
    }

    private suspend fun isInvited(id: InvitationId, userId: UserId): Boolean =
        storage.getInvitation(id)?.invitedUserId == userId

    interface Storage {
        suspend fun getInvitation(id: InvitationId): FullInvitation?
        suspend fun markAsDenied(id: InvitationId): Boolean
    }
}
