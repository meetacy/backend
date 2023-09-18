package app.meetacy.backend.feature.updates.endpoints.integration

import app.meetacy.backend.feature.updates.endpoints.integration.stream.streamUpdates
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.updates(di: DI) = route("/updates") {
    streamUpdates(di)
}
