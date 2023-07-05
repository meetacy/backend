package app.meetacy.backend.database.integration.files

import app.meetacy.backend.database.files.FilesStorage
import app.meetacy.backend.endpoint.files.download.GetFileRepository
import app.meetacy.backend.endpoint.files.download.GetFileResult
import app.meetacy.backend.types.file.FileIdentity
import org.jetbrains.exposed.sql.Database
import java.io.File

class DatabaseGetFileRepository(
    private val filesStorage: FilesStorage,
    private val basePath: String
) : GetFileRepository {
    override suspend fun getFile(fileId: FileIdentity): GetFileResult {
        return when(val description = filesStorage.getFileDescription(fileId.id)) {
            null -> GetFileResult.InvalidFileIdentity
            else -> {
                if (description.fileIdentity.accessHash == fileId.accessHash) {
                    GetFileResult.Success(
                        file = File(basePath, "${fileId.id.long}"),
                        fileName = description.fileName,
                        fileSize = description.fileSize ?: return GetFileResult.InvalidFileIdentity
                    )
                } else {
                    GetFileResult.InvalidFileIdentity
                }
            }
        }
    }
}
