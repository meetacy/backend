package app.meetacy.backend.feature.files.usecase.integration

import app.meetacy.backend.feature.files.endpoints.upload.SaveFileRepository
import app.meetacy.backend.feature.files.endpoints.upload.UploadFileResult
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.feature.files.usecase.files.UploadFileUsecase
import java.io.InputStream

class UsecaseUploadFileRepository(
    private val basePath: String,
    private val usecase: UploadFileUsecase,
    private val filesLimit: FileSize,
    private val deleteFilesOnExit: Boolean
) : SaveFileRepository {
    override suspend fun saveFile(accessIdentity: AccessIdentity, fileName: String, inputProvider: () -> InputStream): UploadFileResult {
        val uploader = UsecaseFileUploader(inputProvider(), basePath, deleteFilesOnExit)
        return when(val result = usecase.saveFile(accessIdentity, uploader, fileName, filesLimit)) {
            is UploadFileUsecase.Result.Success -> UploadFileResult.Success(result.fileIdentity)
            UploadFileUsecase.Result.InvalidIdentity -> UploadFileResult.InvalidIdentity
            is UploadFileUsecase.Result.LimitSize -> UploadFileResult.LimitSize(result.filesSize, result.limitSize)
        }
    }
}
