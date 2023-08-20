package app.meetacy.backend.feature.files.usecase.get

import app.meetacy.backend.types.files.FileDescription
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FileIdentity
import app.meetacy.backend.types.files.FileSize
import java.io.File

class GetFileUsecase(
    private val filesDirectory: File,
    private val storage: Storage
) {

    suspend fun getFile(fileId: FileIdentity): Result {
        val description = storage.getFileDescription(fileId.id)
            ?: return Result.InvalidFileIdentity

        if (description.fileIdentity.accessHash != fileId.accessHash)
            return Result.InvalidFileIdentity

        return Result.Success(
            file = File(filesDirectory, "${fileId.id.long}"),
            fileName = description.fileName,
            fileSize = description.fileSize
        )
    }

    interface Storage {
        suspend fun getFileDescription(id: FileId): FileDescription?
    }

    sealed interface Result {
        data class Success(
            val file: File,
            val fileName: String,
            val fileSize: FileSize
        ) : Result

        data object InvalidFileIdentity : Result
    }
}
