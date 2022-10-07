package app.meetacy.backend.usecase.integration.files

import app.meetacy.backend.endpoint.files.upload.SaveFileRepository
import app.meetacy.backend.endpoint.files.upload.UploadFileResult
import app.meetacy.backend.usecase.files.upload.UploadFileUsecase
import io.ktor.http.content.*

class UsecaseUploadFileRepository(
    private val usecase: UploadFileUsecase
) : SaveFileRepository {
    override suspend fun saveFile(multiPartData: MultiPartData): UploadFileResult {
        TODO("Not yet implemented")
    }
}
