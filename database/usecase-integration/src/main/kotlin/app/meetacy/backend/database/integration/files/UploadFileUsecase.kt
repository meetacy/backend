package app.meetacy.backend.database.integration.files

import app.meetacy.backend.database.files.FilesTable
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.files.upload.UploadFileUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseUploadFileStorage(db: Database) : UploadFileUsecase.Storage {
    private val filesTable = FilesTable(db)
    override suspend fun saveFileDescription(userId: UserId, accessHash: AccessHash): FileIdentity =
        filesTable.saveFileDescription(userId, accessHash)
}
