package app.meetacy.backend.feature.files.database.integration.files

import app.meetacy.backend.feature.files.database.FilesStorage
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.files.usecase.files.UploadFileUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseUploadFileStorage(db: Database) : UploadFileUsecase.Storage {
    private val filesStorage = FilesStorage(db)
    override suspend fun saveFileDescription(userId: UserId, accessHash: AccessHash, fileName: String): FileIdentity =
        filesStorage.saveFileDescription(userId, accessHash, fileName)

    override suspend fun uploadFileSize(fileId: FileId, fileSize: FileSize) {
        filesStorage.updateFileSize(fileId, fileSize)
    }

    override suspend fun getUserWastedSize(userId: UserId): FileSize =
        filesStorage.getUserFullSize(userId)

}
