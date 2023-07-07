package app.meetacy.backend.endpoint.friends.location

import app.meetacy.backend.endpoint.friends.location.stream.StreamLocationRepository
import app.meetacy.backend.endpoint.friends.location.stream.streamFriendsLocation
import io.ktor.server.routing.*

class FriendsLocationDependencies(
    val streamLocationRepository: StreamLocationRepository
)

fun Route.friendsLocation(dependencies: FriendsLocationDependencies) = route("/location") {
    streamFriendsLocation(dependencies.streamLocationRepository)
}
