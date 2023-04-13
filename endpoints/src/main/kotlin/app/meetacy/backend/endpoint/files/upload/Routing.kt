package app.meetacy.backend.endpoint.files.upload

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.serialization.access.serializable
import app.meetacy.backend.types.serialization.file.serializable
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import java.io.InputStream

sealed interface UploadFileResult {
    class Success(val fileIdentity: FileIdentity) : UploadFileResult
    object InvalidIdentity : UploadFileResult
    class LimitSize(val filesSize: FileSize, val filesSizeLimit: Long) : UploadFileResult
}

interface SaveFileRepository {
    suspend fun saveFile(
        accessIdentity: AccessIdentity,
        fileName: String,
        inputProvider: () -> InputStream
    ): UploadFileResult
}

fun Route.upload(provider: SaveFileRepository) = post("/upload") {
    val multipartData = call.receiveMultipart()

    var token: AccessIdentity? = null
    var inputProvider: (() -> InputStream)? = null
    var fileName = "unnamed"

    multipartData.forEachPart { part ->
        when (part) {
            is PartData.FormItem -> {
                if (part.name == "token") token = AccessIdentity.parse(part.value)
            }

            is PartData.FileItem -> {
                inputProvider = part.streamProvider
                fileName = part.originalFileName ?: fileName
            }

            else -> {}
        }
    }

    if (token == null || inputProvider == null) {
        error("Please provide accessIdentity and inputProvider")
    }
    when (val result = provider.saveFile(token!!, fileName, inputProvider!!)) {
        is UploadFileResult.Success -> call.respondSuccess(
            result.fileIdentity.serializable()
        )

        is UploadFileResult.InvalidIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)

        is UploadFileResult.LimitSize -> {
            val filesSizeLimit = result.filesSizeLimit
            val filesSize = result.filesSize.bytesSize

            call.respondFailure(14, "You have exceed your storage limit (max: $filesSizeLimit, now: $filesSize)")
        }
    }
}
