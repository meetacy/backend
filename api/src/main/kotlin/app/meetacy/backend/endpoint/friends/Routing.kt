package app.meetacy.backend.endpoint.friends

import app.meetacy.backend.endpoint.friends.add.AddFriendRepository
import app.meetacy.backend.endpoint.friends.add.addFriend
import app.meetacy.backend.endpoint.friends.get.GetFriendsRepository
import app.meetacy.backend.endpoint.friends.get.getFriend
import io.ktor.server.routing.*


fun Route.friends(
    addFriendRepository: AddFriendRepository,
    getFriendsRepository: GetFriendsRepository
) = route("/friends") {
    addFriend(addFriendRepository)
    getFriend(getFriendsRepository)
}
