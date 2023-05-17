package app.meetacy.backend.usecase.invitations.deny

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.Invitation
import app.meetacy.backend.usecase.types.authorizeWithUserId

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

    suspend fun markAsDenied(token: AccessIdentity, id: InvitationId): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }
        val invitation = storage.getInvitation(id) ?: return Result.NotFound

        if (invitation.expiryDate <= DateTime.now()) return Result.NotFound
        if (!isInvited(id, userId)) return Result.NoPermissions
        storage.markAsDenied(id)

        return Result.Success
    }

    private suspend fun isInvited(id: InvitationId, userId: UserId): Boolean =
        storage.getInvitation(id)?.invitedUserId == userId

    interface Storage {
        suspend fun getInvitation(id: InvitationId): Invitation?
        suspend fun markAsDenied(id: InvitationId): Boolean
    }
}
