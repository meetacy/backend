package app.meetacy.backend.feature.auth.usecase.files

import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.feature.auth.usecase.types.AuthRepository
import app.meetacy.backend.feature.auth.usecase.types.authorizeWithUserId

class UploadFileUsecase(
    private val authRepository: app.meetacy.backend.feature.auth.usecase.types.AuthRepository,
    private val storage: Storage,
    private val hashGenerator: AccessHashGenerator
) {
    sealed interface Result {
        class Success(val fileIdentity: FileIdentity) : Result
        object InvalidIdentity : Result
        class LimitSize(val filesSize: FileSize, val limitSize: FileSize) : Result
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
