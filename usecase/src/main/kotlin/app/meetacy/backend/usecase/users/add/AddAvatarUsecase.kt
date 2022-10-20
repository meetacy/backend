package app.meetacy.backend.usecase.users.add

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FilesRepository
import app.meetacy.backend.usecase.types.authorizeWithFileId
import app.meetacy.backend.usecase.types.authorizeWithUserId

class AddAvatarUsecase(
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
        authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidIdentity }
        filesRepository.authorizeWithFileId(avatarIdentity) { return Result.InvalidAvatarIdentity }
        storage.addAvatar(accessIdentity, avatarIdentity)
        return Result.Success
    }

    interface Storage {
        suspend fun addAvatar (accessIdentity: AccessIdentity, avatarIdentity: FileIdentity)
    }
}
