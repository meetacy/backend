package app.meetacy.backend.feature.friends.endpoints.integration.location

import app.meetacy.backend.feature.friends.endpoints.integration.location.stream.streamFriendsLocation
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.friendsLocation(di: DI) = route("/location") {
    streamFriendsLocation(di)
}
