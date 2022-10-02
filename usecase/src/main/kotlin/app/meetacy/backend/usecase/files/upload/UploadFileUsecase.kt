package app.meetacy.backend.usecase.files.upload

import app.meetacy.backend.types.*
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId

class UploadFileUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {
    sealed interface Result {
        class Success(val fileIdentity: FileIdentity) : Result
        object InvalidIdentity : Result
    }

    suspend fun saveFile(
        accessIdentity: AccessIdentity,
        fileUploader: FileUploader
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidIdentity }
        val fileIdentity = storage.saveFileDescription(userId)
        fileUploader.uploadFile(fileIdentity.fileId)
        return Result.Success(fileIdentity)
    }

    interface Storage {

        suspend fun saveFileDescription(userId: UserId): FileIdentity
    }

    interface FileUploader {
        suspend fun uploadFile(fileId: FileId)
    }
}