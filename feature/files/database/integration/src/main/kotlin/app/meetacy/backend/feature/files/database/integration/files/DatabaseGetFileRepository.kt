package app.meetacy.backend.feature.files.database.integration.files

import app.meetacy.backend.feature.files.database.FilesStorage
import app.meetacy.backend.feature.files.endpoints.download.GetFileRepository
import app.meetacy.backend.feature.files.endpoints.download.GetFileResult
import app.meetacy.backend.types.serializable.file.FileIdentity
import app.meetacy.backend.types.serializable.file.serializable
import app.meetacy.backend.types.serializable.file.type
import java.io.File

class DatabaseGetFileRepository(
    private val filesStorage: FilesStorage,
    private val basePath: String
) : GetFileRepository {
    override suspend fun getFile(fileId: FileIdentity): GetFileResult {
        val fileIdType = fileId.type()
        return when(val description = filesStorage.getFileDescription(fileIdType.id)) {
            null -> GetFileResult.InvalidFileIdentity
            else -> {
                if (description.fileIdentity.accessHash == fileIdType.accessHash) {
                    GetFileResult.Success(
                        file = File(basePath, "${fileIdType.id.long}"),
                        fileName = description.fileName,
                        fileSize = description.fileSize?.serializable() ?: return GetFileResult.InvalidFileIdentity
                    )
                } else {
                    GetFileResult.InvalidFileIdentity
                }
            }
        }
    }
}
