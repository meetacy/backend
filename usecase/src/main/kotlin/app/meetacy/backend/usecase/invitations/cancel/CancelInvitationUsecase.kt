package app.meetacy.backend.usecase.invitations.cancel

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
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

        if (!storage.doesExist(invitationId) || storage.isExpired(invitationId)) return Result.NotFound
        if (!storage.isInvitor(userId, invitationId)) return Result.NoPermissions
        storage.cancel(invitationId)

        return Result.Success
    }

    interface Storage {
        suspend fun isInvitor(userId: UserId, invitationId: InvitationId): Boolean
        suspend fun doesExist(id: InvitationId): Boolean
        suspend fun cancel(id: InvitationId): Boolean
        suspend fun isExpired(id: InvitationId): Boolean
    }
}