package app.meetacy.backend.feature.files.endpoints.upload

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.file.FileIdentity
import app.meetacy.backend.types.serializable.file.FileSize
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import java.io.InputStream

sealed interface UploadFileResult {
    data class Success(val fileIdentity: FileIdentity) : UploadFileResult
    data object InvalidIdentity : UploadFileResult
    data class LimitSize(val filesSize: FileSize, val filesSizeLimit: FileSize) : UploadFileResult
}

interface SaveFileRepository {
    suspend fun saveFile(
        accessIdentity: AccessIdentity,
        fileName: String,
        inputProvider: () -> InputStream
    ): UploadFileResult
}

fun Route.upload(saveFileRepository: SaveFileRepository) = post("/upload") {
    val multipartData = call.receiveMultipart()

    var token: AccessIdentity? = null
    var inputProvider: (() -> InputStream)? = null
    var fileName = "unnamed"

    val partsToDispose = mutableListOf<PartData>()

    multipartData.forEachPart { part ->
        when (part) {
            is PartData.FormItem -> {
                if (part.name == "token") token = AccessIdentity(part.value)
            }

            is PartData.FileItem -> {
                inputProvider = part.streamProvider
                fileName = part.originalFileName ?: fileName
            }

            else -> {}
        }
        partsToDispose += part
    }

    if (token == null) {
        error("Please provide token")
    }
    if (inputProvider == null) {
        error("Please provide file part")
    }

    when (val result = saveFileRepository.saveFile(token!!, fileName, inputProvider!!)) {
        is UploadFileResult.Success -> call.respondSuccess(result.fileIdentity)
        is UploadFileResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        is UploadFileResult.LimitSize -> {
            val filesSizeLimit = result.filesSizeLimit
            val filesSize = result.filesSize.bytesSize

            call.respondFailure(14, "You have exceed your storage limit (max: $filesSizeLimit, now: $filesSize)")
        }
    }

    partsToDispose.forEach { it.dispose() }
}
