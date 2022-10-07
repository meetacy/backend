package app.meetacy.backend.endpoint.files

import app.meetacy.backend.endpoint.files.download.GetFileRepository
import app.meetacy.backend.endpoint.files.download.download
import app.meetacy.backend.endpoint.files.upload.SaveFileRepository
import app.meetacy.backend.endpoint.files.upload.upload
import io.ktor.server.routing.*

class FilesDependencies(
    val saveFileRepository: SaveFileRepository,
    val getFileRepository: GetFileRepository
)

fun Route.files(dependencies: FilesDependencies) = route("/files") {
    download(dependencies.getFileRepository)
    upload(dependencies.saveFileRepository)
}
