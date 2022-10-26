package app.meetacy.backend.usecase.users.avatar.delete

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.usecase.types.*

class DeleteUserAvatarUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {
    sealed interface Result {
        object Success : Result
        object InvalidIdentity : Result

    }

    suspend fun deleteAvatar(
        accessIdentity: AccessIdentity
    ): Result {
        authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidIdentity }
        storage.deleteAvatar(accessIdentity)

        return Result.Success
    }

    interface Storage {
        suspend fun deleteAvatar (accessIdentity: AccessIdentity)
    }
}
