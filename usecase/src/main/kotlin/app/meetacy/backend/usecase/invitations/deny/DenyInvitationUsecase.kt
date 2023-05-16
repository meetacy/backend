package app.meetacy.backend.usecase.invitations.deny

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
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

        if (!storage.doesExist(id) || storage.isExpired(id)) return Result.NotFound
        if (!storage.isInvited(id, userId)) return Result.NoPermissions
        storage.markAsDenied(id)

        return Result.Success
    }

    interface Storage {
        suspend fun isInvited(invitation: InvitationId, user: UserId): Boolean
        suspend fun doesExist(id: InvitationId): Boolean
        suspend fun markAsDenied(id: InvitationId): Boolean
        suspend fun isExpired(id: InvitationId): Boolean
    }
}
