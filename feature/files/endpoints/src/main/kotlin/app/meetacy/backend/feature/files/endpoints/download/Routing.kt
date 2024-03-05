package app.meetacy.backend.feature.files.endpoints.download

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.types.serializable.file.FileId
import app.meetacy.backend.types.serializable.file.FileSize
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

sealed interface GetFileResult {
    data class Success(val file: File, val fileName: String, val fileSize: FileSize) : GetFileResult
    data object InvalidFileIdentity : GetFileResult
}

interface GetFileRepository {
    suspend fun getFile(fileId: FileId): GetFileResult
}

fun Route.download(getFileRepository: GetFileRepository) = get("/download") {

    val fileId = FileId(call.parameters["fileId"]!!)

    when (val result = getFileRepository.getFile(fileId)) {
        GetFileResult.InvalidFileIdentity -> call.respondFailure(Failure.InvalidFileIdentity)

        is GetFileResult.Success -> {
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(
                    ContentDisposition.Parameters.Size, "${result.fileSize.bytesSize}"
                ).withParameter(
                    ContentDisposition.Parameters.FileName, result.fileName
                ).toString()
            )
            call.respondFile(result.file, contentType = ContentType.defaultForFilePath(result.fileName))
        }
    }
}

suspend fun ApplicationCall.respondFile(file: File, contentType: ContentType = ContentType.defaultForFile(file)) {
    respondOutputStream(contentType, contentLength = file.length()) {
        withContext(Dispatchers.IO) {
            FileInputStream(file).transferTo(this@respondOutputStream)
        }
    }
}
