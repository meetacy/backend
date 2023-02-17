package app.meetacy.backend.database.integration.files

import app.meetacy.backend.database.files.FilesTable
import app.meetacy.backend.endpoint.files.download.GetFileRepository
import app.meetacy.backend.endpoint.files.download.GetFileResult
import app.meetacy.backend.types.FileIdentity
import org.jetbrains.exposed.sql.Database
import java.io.File

class DatabaseGetFileRepository(
    database: Database,
    private val basePath: String
) : GetFileRepository {
    private val filesTable = FilesTable(database)
    override suspend fun getFile(fileIdentity: FileIdentity): GetFileResult =
        when(val description = filesTable.getFileDescription(fileIdentity.fileId)) {
            null -> GetFileResult.InvalidFileIdentity
            else -> {
                if (description.fileIdentity.accessHash == fileIdentity.accessHash) {
                    GetFileResult.Success(
                        file = File(basePath, "${fileIdentity.fileId.long}"),
                        fileName = description.fileName,
                        fileSize = if (description.fileSize != null) description.fileSize else null
                    )
                } else {
                    GetFileResult.InvalidFileIdentity
                }
            }
        }
}
