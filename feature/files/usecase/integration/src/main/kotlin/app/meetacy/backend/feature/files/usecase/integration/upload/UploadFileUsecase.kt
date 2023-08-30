package app.meetacy.backend.feature.files.usecase.integration.upload

import app.meetacy.backend.feature.files.database.FilesStorage
import app.meetacy.backend.feature.files.usecase.upload.UploadFileUsecase
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FileSize
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.uploadFileUsecase() {
    val uploadFileUsecase by singleton {
        val authRepository: AuthRepository by getting
        val accessHashGenerator: AccessHashGenerator by getting
        val filesStorage: FilesStorage by getting

        val storage = object : UploadFileUsecase.Storage {
            override suspend fun saveFileDescription(
                userId: UserId,
                accessHash: AccessHash,
                fileName: String
            ) = filesStorage.saveFileDescription(userId, accessHash, fileName)

            override suspend fun uploadFileSize(
                fileId: FileId,
                fileSize: FileSize
            ) {
                filesStorage.updateFileSize(fileId, fileSize)
            }

            override suspend fun getUserWastedSize(
                userId: UserId
            ): FileSize = filesStorage.getUserFullSize(userId)
        }

        UploadFileUsecase(
            authRepository = authRepository,
            storage = storage,
            hashGenerator = accessHashGenerator
        )
    }
}
