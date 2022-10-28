package app.meetacy.backend.endpoint.files.upload

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.types.FileSize
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import app.meetacy.backend.types.serialization.serializable
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.io.InputStream

sealed interface UploadFileResult {
    class Success(val fileIdentity: FileIdentity) : UploadFileResult
    object InvalidIdentity : UploadFileResult
    class LimitSize(val filesSize: FileSize, val filesSizeLimit: Long) : UploadFileResult
}
@Serializable
class UploadFileResponse(
    val status: Boolean,
    val errorCode: Int?,
    val availableBytes: Long? = null,
    val wastedBytes: Long? = null,
    val errorMessage: String?,
    val result: FileIdentitySerializable?
)

interface SaveFileRepository {
    suspend fun saveFile(accessIdentity: AccessIdentity, fileName: String, inputProvider: () -> InputStream): UploadFileResult
}

fun Route.upload(provider: SaveFileRepository) = post("/upload") {
    val multipartData = call.receiveMultipart()

    var accessIdentity: AccessIdentity? = null
    var inputProvider: (() -> InputStream)? = null
    var fileName = "unnamed"

    multipartData.forEachPart { part ->
        when (part) {
            is PartData.FormItem -> {
                if (part.name == "accessIdentity") accessIdentity = AccessIdentity.parse(part.value)
            }
            is PartData.FileItem -> {
                inputProvider = part.streamProvider
                fileName = part.originalFileName ?: fileName
            }
            else -> {}
        }
    }

    if (accessIdentity == null || inputProvider == null) {
        error("Please provide accessIdentity and inputProvider")
    }
    when (val result = provider.saveFile(accessIdentity!!, fileName, inputProvider!!)) {
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
        is UploadFileResult.LimitSize -> {
            val filesSizeLimit = result.filesSizeLimit
            val filesSize = result.filesSize.bytesSize

            call.respond(
                UploadFileResponse(
                    status = false,
                    errorCode = 2,
                    availableBytes = filesSizeLimit,
                    wastedBytes = filesSize,
                    errorMessage = "You have exceed your storage limit (max: $filesSizeLimit, now: $filesSize)",
                    result = null
                )
            )
        }
    }
}
