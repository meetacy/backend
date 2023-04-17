package app.meetacy.backend.usecase.files

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.HashGenerator
import app.meetacy.backend.usecase.types.authorizeWithUserId

class UploadFileUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val hashGenerator: HashGenerator
) {
    sealed interface Result {
        class Success(val fileIdentity: FileIdentity) : Result
        object InvalidIdentity : Result
        class LimitSize(val filesSize: FileSize, val limitSize: Long) : Result
    }

    suspend fun saveFile(
        accessIdentity: AccessIdentity,
        fileUploader: FileUploader,
        fileName: String,
        filesLimit: Long
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidIdentity }
        val accessHash = AccessHash(hashGenerator.generate())
        val fileIdentity = storage.saveFileDescription(userId, accessHash, fileName)
        val wastedSize = storage.getUserFullSize(userId)
        val userFilesLimit = FileSize(filesLimit - wastedSize.bytesSize)

        return when (val fileSize = fileUploader.uploadFile(fileIdentity.id, userFilesLimit)) {
            null -> Result.LimitSize(wastedSize, filesLimit)
            else -> {
                storage.uploadFileSize(userId, fileIdentity.id, fileSize)
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
        suspend fun uploadFileSize(userId: UserId, fileId: FileId, fileSize: FileSize)
        suspend fun getUserFullSize(userId: UserId): FileSize
    }

    interface FileUploader {
        suspend fun uploadFile(
            fileId: FileId,
            userFilesFreeLimit: FileSize
        ): FileSize?
    }
}
