package app.meetacy.backend.usecase.users.avatar.add

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.usecase.types.*

class AddUserAvatarUsecase(
    private val authRepository: AuthRepository,
    private val filesRepository: FilesRepository,
    private val storage: Storage
) {
    sealed interface Result {
        object Success : Result
        object InvalidIdentity : Result
        object InvalidAvatarIdentity : Result
    }

    suspend fun addAvatar(
        accessIdentity: AccessIdentity,
        avatarIdentity: FileIdentity
    ): Result {
        authRepository.authorize(accessIdentity) { return Result.InvalidIdentity }
        if (!filesRepository.checkFile(avatarIdentity)) return Result.InvalidAvatarIdentity

        storage.addAvatar(accessIdentity, avatarIdentity)
        return Result.Success
    }

    interface Storage {
        suspend fun addAvatar (accessIdentity: AccessIdentity, avatarIdentity: FileIdentity)
    }
}