package app.meetacy.backend.endpoint.files.download


import app.meetacy.backend.types.FileId
import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
data class DownloadParams(
    val fileIdentity: FileIdentitySerializable
)

sealed interface S {}

interface GetFileRepository {
    suspend fun getFile (fileIdentity: FileIdentity): FileId
}

fun Route.download(getFileRepository: GetFileRepository) = get("/download") {
    val fileIdentity = call.receive<FileIdentity>()
    val pathFile = getFileRepository.getFile(fileIdentity)
    val file = File("D:/y9Kap/Downloads/Documents/$pathFile")
    call.response.header(
        HttpHeaders.ContentDisposition,
        ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "$pathFile")
            .toString()
    )
    call.respondFile(file)
}
fun Route.download() = get("/download") {
    val file = File("D:/y9Kap/Downloads/Documents/Untitled.pdf")
    call.response.header(
        HttpHeaders.ContentDisposition,
        ContentDisposition.Attachment.withParameter(
            ContentDisposition.Parameters.Size,
            "Untitled.pdf"
        )
            .toString()
    )
    call.respondFile(file)
}

