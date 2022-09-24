package app.meetacy.backend.usecase.files.upload

import app.meetacy.backend.types.*
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId

class UploadFileUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val uploadRepository: UploadRepository
) {
    sealed interface Result {
        class Success(val fileIdentity: FileIdentity) : Result
        object InvalidIdentity : Result
    }

    suspend fun saveFile(
        accessIdentity: AccessIdentity,
        action: Action
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidIdentity }
        storage.saveDescriptor(userId)
        val fileIdentity = storage.upload(accessIdentity)
        action.saveFile(fileIdentity.fileId)
        return Result.Success(fileIdentity)
    }

    interface Storage {

        suspend fun saveDescriptor(userId: UserId): FileIdentity
    }

    interface Action {
        suspend fun saveFile(fileId: FileId)
    }
}