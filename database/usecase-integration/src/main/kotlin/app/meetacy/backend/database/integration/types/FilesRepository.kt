package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.files.FilesTable
import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.usecase.types.FilesRepository
import org.jetbrains.exposed.sql.Database

class DatabaseFilesRepository(db: Database) : FilesRepository {
    private val filesTable = FilesTable(db)
    override suspend fun checkFile(identity: FileIdentity) = filesTable.checkFileIdentity(identity)
}
