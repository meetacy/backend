package app.meetacy.backend.feature.updates.endpoints.updates

import app.meetacy.backend.feature.updates.endpoints.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.feature.updates.endpoints.updates.stream.streamUpdates
import io.ktor.server.routing.*

fun Route.updates(dependency: StreamUpdatesRepository) = route("/updates") {
    streamUpdates(dependency)
}
