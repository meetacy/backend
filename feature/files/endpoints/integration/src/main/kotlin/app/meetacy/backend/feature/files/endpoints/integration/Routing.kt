package app.meetacy.backend.feature.files.endpoints.integration

import app.meetacy.backend.feature.files.endpoints.integration.download.download
import app.meetacy.backend.feature.files.endpoints.integration.upload.upload
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.files(di: DI) = route("/files") {
    download(di)
    upload(di)
}
