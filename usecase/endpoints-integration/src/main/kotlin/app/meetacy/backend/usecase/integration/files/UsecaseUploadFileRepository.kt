package app.meetacy.backend.usecase.integration.files

import app.meetacy.backend.endpoint.files.upload.SaveFileRepository
import app.meetacy.backend.endpoint.files.upload.UploadFileResult
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.usecase.files.UploadFileUsecase
import java.io.InputStream

class UsecaseUploadFileRepository(
    private val basePath: String,
    private val usecase: UploadFileUsecase,
    private val filesLimit: Long
) : SaveFileRepository {
    override suspend fun saveFile(accessIdentity: AccessIdentity, fileName: String, inputProvider: () -> InputStream): UploadFileResult {
        val uploader = UsecaseFileUploader(inputProvider(), basePath)
        return when(val result = usecase.saveFile(accessIdentity, uploader, fileName, filesLimit)) {
            is UploadFileUsecase.Result.Success -> UploadFileResult.Success(result.fileIdentity)
            UploadFileUsecase.Result.InvalidIdentity -> UploadFileResult.InvalidIdentity
            is UploadFileUsecase.Result.LimitSize -> UploadFileResult.LimitSize(result.filesSize, result.limitSize)
        }
    }
}
