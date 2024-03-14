package app.meetacy.backend.feature.friends.endpoints.integration

import app.meetacy.backend.feature.friends.endpoints.integration.add.addFriend
import app.meetacy.backend.feature.friends.endpoints.integration.delete.deleteFriend
import app.meetacy.backend.feature.friends.endpoints.integration.list.listFriends
import app.meetacy.backend.feature.friends.endpoints.integration.location.friendsLocation
import app.meetacy.backend.feature.friends.endpoints.integration.subscribers.subscribers
import app.meetacy.backend.feature.friends.endpoints.integration.subscriptions.subscriptions
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.friends(di: DI) = route("/friends") {
    addFriend(di)
    deleteFriend(di)
    listFriends(di)
    subscribers(di)
    subscriptions(di)
    friendsLocation(di)
}
