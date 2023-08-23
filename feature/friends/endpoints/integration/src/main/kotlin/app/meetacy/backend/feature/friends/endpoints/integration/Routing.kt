package app.meetacy.backend.feature.friends.endpoints.integration

import app.meetacy.backend.feature.friends.endpoints.integration.add.addFriend
import app.meetacy.backend.feature.friends.endpoints.integration.delete.deleteFriend
import app.meetacy.backend.feature.friends.endpoints.integration.list.listFriends
import app.meetacy.backend.feature.friends.endpoints.integration.location.location
import io.ktor.server.routing.*

fun Route.friends() = route("/friends") {
    addFriend()
    deleteFriend()
    listFriends()
    location()
}
