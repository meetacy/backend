package app.meetacy.backend.feature.files.endpoints.integration

import app.meetacy.backend.feature.files.endpoints.integration.download.download
import app.meetacy.backend.feature.files.endpoints.integration.upload.upload
import io.ktor.server.routing.*

fun Route.files() = route("/files") {
    download()
    upload()
}
