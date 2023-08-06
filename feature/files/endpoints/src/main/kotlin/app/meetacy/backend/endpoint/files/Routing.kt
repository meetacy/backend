package app.meetacy.backend.endpoint.files

import app.meetacy.backend.endpoint.files.download.download
import app.meetacy.backend.endpoint.files.upload.upload
import io.ktor.server.routing.*

fun Route.files() = route("/files") {
    download()
    upload()
}
