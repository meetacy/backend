package app.meetacy.backend.feature.files.usecase.upload

import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FileIdentity
import app.meetacy.backend.types.files.FileSize
import app.meetacy.backend.types.users.UserId

class UploadFileUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val hashGenerator: AccessHashGenerator
) {
    sealed interface Result {
        data class Success(val fileIdentity: FileIdentity) : Result
        data object InvalidIdentity : Result
        data class LimitSize(val filesSize: FileSize, val limitSize: FileSize) : Result
    }

    suspend fun saveFile(
        accessIdentity: AccessIdentity,
        fileUploader: FileUploader,
        fileName: String,
        filesLimit: FileSize
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidIdentity }
        val accessHash = AccessHash(hashGenerator.generate())
        val fileIdentity = storage.saveFileDescription(userId, accessHash, fileName)
        val wastedSize = storage.getUserWastedSize(userId)
        val userFilesLimit = FileSize(filesLimit.bytesSize - wastedSize.bytesSize)

        return when (val fileSize = fileUploader.uploadFile(fileIdentity.id, userFilesLimit)) {
            null -> Result.LimitSize(wastedSize, filesLimit)
            else -> {
                storage.uploadFileSize(fileIdentity.id, fileSize)
                Result.Success(fileIdentity)
            }
        }
    }

    interface Storage {
        suspend fun saveFileDescription(
            userId: UserId,
            accessHash: AccessHash,
            fileName: String
        ): FileIdentity
        suspend fun uploadFileSize(fileId: FileId, fileSize: FileSize)
        suspend fun getUserWastedSize(userId: UserId): FileSize
    }

    interface FileUploader {
        suspend fun uploadFile(
            fileId: FileId,
            userFilesFreeLimit: FileSize
        ): FileSize?
    }
}
