package app.meetacy.backend.endpoint.updates

import app.meetacy.backend.endpoint.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.endpoint.updates.stream.streamUpdates
import io.ktor.server.routing.*

class UpdatesDependencies(
    val streamUpdatesRepository: StreamUpdatesRepository
)

fun Route.updates(dependencies: UpdatesDependencies) {
    streamUpdates(dependencies.streamUpdatesRepository)
}
