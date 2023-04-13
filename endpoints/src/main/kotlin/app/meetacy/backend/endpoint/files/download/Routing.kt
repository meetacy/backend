package app.meetacy.backend.endpoint.files.download

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.file.FileSize
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

sealed interface GetFileResult {
    class Success(val file: File, val fileName: String, val fileSize: FileSize?) : GetFileResult
    object InvalidFileIdentity : GetFileResult
}

interface GetFileRepository {
    suspend fun getFile(fileId: FileIdentity): GetFileResult
}

fun Route.download(getFileRepository: GetFileRepository) = get("/download") {
    val fileIdentity = FileIdentity.parse(
        call.parameters["fileId"]!!
    )!!
    when (val result = getFileRepository.getFile(fileIdentity)) {
        GetFileResult.InvalidFileIdentity -> call.respondFailure(Failure.InvalidFileIdentity)

        is GetFileResult.Success -> {
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(
                    ContentDisposition.Parameters.Size, "${result.fileSize ?: 0}"
                ).withParameter(
                    ContentDisposition.Parameters.FileName, result.fileName
                ).toString()
            )
            call.respondFile(result.file, contentType = ContentType.defaultForFilePath(result.fileName))
        }
    }
}

suspend fun ApplicationCall.respondFile(file: File, contentType: ContentType = ContentType.defaultForFile(file)) {
    respondOutputStream(contentType = contentType, contentLength = file.length()) {
        withContext(Dispatchers.IO) {
            FileInputStream(file).transferTo(this@respondOutputStream)
        }
    }
}
