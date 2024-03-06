package app.meetacy.backend.feature.files.endpoints.integration.download

import app.meetacy.backend.feature.files.endpoints.download.GetFileRepository
import app.meetacy.backend.feature.files.endpoints.download.GetFileResult
import app.meetacy.backend.feature.files.endpoints.download.download
import app.meetacy.backend.feature.files.usecase.get.GetFileUsecase
import app.meetacy.backend.types.serializable.file.FileId
import app.meetacy.backend.types.serializable.file.serializable
import app.meetacy.backend.types.serializable.file.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.download(di: DI) {
    val getFileUsecase: GetFileUsecase by di.getting

    val repository = object : GetFileRepository {
        override suspend fun getFile(
            fileId: FileId
        ): GetFileResult = when (val result = getFileUsecase.getFile(fileId.type())) {
            is GetFileUsecase.Result.InvalidFileIdentity -> GetFileResult.InvalidFileIdentity
            is GetFileUsecase.Result.Success -> GetFileResult.Success(
                file = result.file,
                fileName = result.fileName,
                fileSize = result.fileSize.serializable()
            )
        }
    }

    download(repository)
}
