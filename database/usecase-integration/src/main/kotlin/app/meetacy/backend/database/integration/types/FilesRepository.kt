package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.files.FilesTable
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.usecase.types.FilesRepository
import org.jetbrains.exposed.sql.Database

class DatabaseFilesRepository(db: Database) : FilesRepository {
    private val filesTable = FilesTable(db)
    override suspend fun checkFile(identity: FileIdentity) = filesTable.checkFile(identity)
    override suspend fun checkFileIdentity(identity: FileIdentity): FileIdentity? = filesTable.checkFileIdentity(identity)

    override suspend fun getFileIdentity(fileId: FileId) = filesTable.getFileIdentity(fileId)
    override suspend fun getFileIdentityList(fileIdList: List<FileId?>): List<FileIdentity?> =
        filesTable.getFileIdentityList(fileIdList)
}
