package app.meetacy.backend.feature.updates.endpoints.updates

import app.meetacy.backend.feature.updates.endpoints.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.feature.updates.endpoints.updates.stream.streamUpdates
import io.ktor.server.routing.*

class UpdatesDependencies(
    val streamUpdatesRepository: StreamUpdatesRepository
)

fun Route.updates(dependencies: UpdatesDependencies) = route("/updates") {
    streamUpdates(dependencies.streamUpdatesRepository)
}
