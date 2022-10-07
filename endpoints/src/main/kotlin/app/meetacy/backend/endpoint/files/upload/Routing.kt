package app.meetacy.backend.endpoint.files.upload

import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import app.meetacy.backend.types.serialization.serializable
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

sealed interface UploadFileResult {
    class Success(val fileIdentity: FileIdentity) : UploadFileResult
    object InvalidIdentity : UploadFileResult
}
@Serializable
class UploadFileResponse(
    val status: Boolean,
    val errorCode: Int?,
    val errorMessage: String?,
    val result: FileIdentitySerializable?
)

interface SaveFileRepository {
    suspend fun saveFile(multiPartData: MultiPartData): UploadFileResult
}

fun Route.upload(provider: SaveFileRepository) = post("/upload") {
    val multipartData = call.receiveMultipart()

    when(val result = provider.saveFile(multipartData)) {
        is UploadFileResult.Success -> call.respond(
            UploadFileResponse(
                status = true,
                errorCode = null,
                errorMessage = null,
                result = result.fileIdentity.serializable()
            )
        )
        is UploadFileResult.InvalidIdentity -> call.respond(
            UploadFileResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid identity",
                result = null
            )
        )
    }

}
