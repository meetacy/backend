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
        object UserNotFound: Result
    }

    suspend fun InvitationId.markAsDenied(token: AccessIdentity): Result {
        val userId = authRepository.authorizeWithUserId(token) { return Result.Unauthorized }

        with(storage) {
            if (!userId.doesExist()) return Result.UserNotFound
            if (!doesExist() || isExpired()) return Result.NotFound
            if (!userId.isInvited(this@markAsDenied)) return Result.NoPermissions  // yes, I love smart this
            markAsDenied()
        }
        return Result.Success
    }

    interface Storage {
        suspend fun UserId.doesExist(): Boolean
        suspend fun UserId.isInvited(invitation: InvitationId): Boolean
        suspend fun InvitationId.doesExist(): Boolean
        suspend fun InvitationId.markAsDenied(): Boolean
        suspend fun InvitationId.isExpired(): Boolean
    }
}