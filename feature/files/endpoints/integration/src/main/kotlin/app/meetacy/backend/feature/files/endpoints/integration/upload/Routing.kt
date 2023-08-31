package app.meetacy.backend.feature.files.endpoints.integration.upload

import app.meetacy.backend.feature.files.endpoints.upload.SaveFileRepository
import app.meetacy.backend.feature.files.endpoints.upload.UploadFileResult
import app.meetacy.backend.feature.files.endpoints.upload.upload
import app.meetacy.backend.feature.files.usecase.upload.UploadFileUsecase
import app.meetacy.backend.feature.files.usecase.upload.UploadFileUsecase.Result
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FileSize
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.file.serializable
import app.meetacy.di.DI
import io.ktor.server.routing.*
import java.io.File
import java.io.InputStream

fun Route.upload(di: DI) {
    val filesSizeLimit: FileSize by di.getting
    val filesBasePath: String by di.getting
    val deleteFilesOnExit: Boolean by di.getting
    val uploadFileUsecase: UploadFileUsecase by di.getting

    val saveFileRepository = object : SaveFileRepository {
        override suspend fun saveFile(
            accessIdentity: AccessIdentity,
            fileName: String,
            inputProvider: () -> InputStream
        ): UploadFileResult {
            val fileUploader = object : UploadFileUsecase.FileUploader {
                override suspend fun uploadFile(
                    fileId: FileId,
                    userFilesFreeLimit: FileSize
                ): FileSize? {
                    val file = File(filesBasePath, "${fileId.long}").apply {
                        if (deleteFilesOnExit) deleteOnExit()
                    }

                    return JvmFileUploader.upload(
                        inputStream = inputProvider(),
                        file = file,
                        limit = userFilesFreeLimit
                    )
                }
            }

            return when (
                val result = uploadFileUsecase.saveFile(
                    accessIdentity.type(),
                    fileUploader = fileUploader,
                    fileName = fileName,
                    filesLimit = filesSizeLimit
                )
            ) {
                is Result.InvalidIdentity -> UploadFileResult.InvalidIdentity
                is Result.LimitSize -> UploadFileResult.LimitSize(
                    filesSize = result.filesSize.serializable(),
                    filesSizeLimit = result.limitSize.serializable()
                )
                is Result.Success -> UploadFileResult.Success(
                    fileIdentity = result.fileIdentity.serializable()
                )
            }
        }

    }

    upload(saveFileRepository)
}
