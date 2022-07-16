package app.meetacy.backend.endpoint.friends

import app.meetacy.backend.endpoint.friends.addNew.AddFriendsProvider
import app.meetacy.backend.endpoint.friends.addNew.addFriend
import app.meetacy.backend.endpoint.friends.get.GetFriendsProvider
import app.meetacy.backend.endpoint.friends.get.getFriend
import io.ktor.server.routing.*


fun Route.friends(
    provider: AddFriendsProvider,
    getProvider: GetFriendsProvider
) = route("/friends") {
    addFriend(provider)
    getFriend(getProvider)
}
