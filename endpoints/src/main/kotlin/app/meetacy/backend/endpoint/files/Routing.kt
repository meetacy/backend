package app.meetacy.backend.endpoint.files

import app.meetacy.backend.endpoint.files.download.download
import app.meetacy.backend.endpoint.files.upload.SaveFileRepository
import app.meetacy.backend.endpoint.files.upload.upload
import io.ktor.server.routing.*

class FilesDependencies(
    val saveFileRepository: SaveFileRepository
)

fun Route.files(dependencies: FilesDependencies) = route("/files") {
    download()
    upload(dependencies.saveFileRepository)
}