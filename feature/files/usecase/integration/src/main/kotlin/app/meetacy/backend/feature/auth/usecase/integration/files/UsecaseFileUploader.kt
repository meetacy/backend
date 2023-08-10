package app.meetacy.backend.feature.auth.usecase.integration.files

import app.meetacy.backend.endpoint.files.repository.JvmFileUploader
import app.meetacy.backend.feature.auth.usecase.files.UploadFileUsecase
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileSize
import java.io.File
import java.io.InputStream

class UsecaseFileUploader(
    private val inputStream: InputStream,
    private val basePath: String,
    private val deleteFilesOnExit: Boolean
): UploadFileUsecase.FileUploader {
    override suspend fun uploadFile(fileId: FileId, userFilesFreeLimit: FileSize): FileSize? {
        val file = File(basePath, "${fileId.long}").apply {
            if (deleteFilesOnExit) deleteOnExit()
        }
        return JvmFileUploader.upload(inputStream, file, userFilesFreeLimit)
    }
}
