package app.meetacy.backend.endpoint.files.download

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File


import app.meetacy.backend.types.FileId
import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.partialcontent.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.Identity.decode
import kotlinx.serialization.Serializable
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
    val file = File("$pathFile")
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
            ContentDisposition.Parameters.FileName,
            "Untitled.pdf"
        )
            .toString()
    )
    call.respondFile(file)
}

