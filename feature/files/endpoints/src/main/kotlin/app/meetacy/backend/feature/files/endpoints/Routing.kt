package app.meetacy.backend.feature.files.endpoints

import app.meetacy.backend.feature.files.endpoints.download.download
import app.meetacy.backend.feature.files.endpoints.upload.upload
import io.ktor.server.routing.*

fun Route.files() = route("/files") {
    download()
    upload()
}
