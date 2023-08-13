package app.meetacy.backend.feature.friends.endpoints.location

import app.meetacy.backend.feature.friends.endpoints.location.stream.StreamLocationRepository
import app.meetacy.backend.feature.friends.endpoints.location.stream.streamFriendsLocation
import io.ktor.server.routing.*

class FriendsLocationDependencies(
    val streamLocationRepository: StreamLocationRepository
)

fun Route.friendsLocation(dependencies: FriendsLocationDependencies) = route("/location") {
    streamFriendsLocation(dependencies.streamLocationRepository)
}
