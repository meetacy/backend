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

    suspend fun InvitationId.cancel(token: AccessIdentity): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }

        with(storage) {
            if (!doesExist() || isExpired()) return Result.NotFound
            if (!userId.isInvitor(this@cancel)) return Result.NoPermissions
            cancel()
        }

        return Result.Success
    }

    interface Storage {
        suspend fun UserId.isInvitor(invitationId: InvitationId): Boolean
        suspend fun InvitationId.doesExist(): Boolean
        suspend fun InvitationId.cancel(): Boolean
        suspend fun InvitationId.isExpired(): Boolean
    }
}