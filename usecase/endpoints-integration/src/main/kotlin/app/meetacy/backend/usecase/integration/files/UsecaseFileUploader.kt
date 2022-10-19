package app.meetacy.backend.usecase.integration.files

import app.meetacy.backend.endpoint.repository.JvmFileUploader
import app.meetacy.backend.types.FileId
import app.meetacy.backend.types.FileSize
import app.meetacy.backend.usecase.files.upload.UploadFileUsecase
import java.io.File
import java.io.InputStream

class UsecaseFileUploader(
    private val inputStream: InputStream,
    private val basePath: String
): UploadFileUsecase.FileUploader {
    override suspend fun uploadFile(fileId: FileId, userFilesFreeLimit: FileSize): FileSize? =
        JvmFileUploader.upload(inputStream, File(basePath, "${fileId.long}"), userFilesFreeLimit)

}
