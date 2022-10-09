package app.meetacy.backend.database.integration.files

import app.meetacy.backend.database.files.FilesTable
import app.meetacy.backend.types.*
import app.meetacy.backend.usecase.files.upload.UploadFileUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseUploadFileStorage(db: Database) : UploadFileUsecase.Storage {
    private val filesTable = FilesTable(db)
    override suspend fun saveFileDescription(userId: UserId, accessHash: AccessHash, fileName: String): FileIdentity =
        filesTable.saveFileDescription(userId, accessHash, fileName)

    override suspend fun uploadFileSize(fileId: FileId, fileSize: FileSize) {
        filesTable.updateFileSize(fileId, fileSize)
    }
}
