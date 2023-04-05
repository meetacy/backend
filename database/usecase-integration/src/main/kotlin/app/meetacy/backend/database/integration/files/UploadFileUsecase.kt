package app.meetacy.backend.database.integration.files

import app.meetacy.backend.database.files.FilesTable
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.files.UploadFileUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseUploadFileStorage(db: Database) : UploadFileUsecase.Storage {
    private val filesTable = FilesTable(db)
    override suspend fun saveFileDescription(userId: UserId, accessHash: AccessHash, fileName: String): FileIdentity =
        filesTable.saveFileDescription(userId, accessHash, fileName)

    override suspend fun uploadFileSize(userId: UserId, fileId: FileId, fileSize: FileSize) {
        filesTable.updateFileSize(fileId, fileSize)
    }

    override suspend fun getUserFullSize(userId: UserId): FileSize =
        filesTable.getUserFullSize(userId)

}
