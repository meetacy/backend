package app.meetacy.backend.endpoint.files.download


import app.meetacy.backend.types.FileIdentity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

sealed interface GetFileResult {
    class Success(val file: File) : GetFileResult
    object InvalidIdentity : GetFileResult
}

class DownloadFileResponse(
    val status: Boolean,
    val errorCode: Int?,
    val errorMessage: String?,
)
interface GetFileRepository {
    suspend fun getFile (fileIdentity: FileIdentity): GetFileResult
}

fun Route.download(getFileRepository: GetFileRepository) = get("/download") {
    val fileIdentity = call.receive<FileIdentity>()
    when(val result = getFileRepository.getFile(fileIdentity)) {
        GetFileResult.InvalidIdentity -> call.respond(
            DownloadFileResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid identity"
            )
        )
        is GetFileResult.Success -> {
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(
                    ContentDisposition.Parameters.Size, "${result.file.length()}"
                ).toString()
            )

            call.respondFile(result.file)
        }
    }

}

