package app.meetacy.backend.feature.files.database.integration.types

import app.meetacy.backend.feature.files.database.FilesStorage
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.feature.files.usecase.types.FilesRepository
import org.jetbrains.exposed.sql.Database

class DatabaseFilesRepository(db: Database) : FilesRepository {
    private val filesStorage = FilesStorage(db)

    override suspend fun getFileIdentities(fileIdList: List<FileId>): List<FileIdentity?> =
        filesStorage.getFileIdentityList(fileIdList)
}
