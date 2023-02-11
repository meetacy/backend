package app.meetacy.backend.endpoint.friends

import app.meetacy.backend.endpoint.friends.add.AddFriendRepository
import app.meetacy.backend.endpoint.friends.add.addFriend
import app.meetacy.backend.endpoint.friends.delete.DeleteFriendRepository
import app.meetacy.backend.endpoint.friends.delete.deleteFriend
import app.meetacy.backend.endpoint.friends.get.GetFriendsRepository
import app.meetacy.backend.endpoint.friends.get.getFriend
import io.ktor.server.routing.*

class FriendsDependencies(
    val addFriendRepository: AddFriendRepository,
    val getFriendsRepository: GetFriendsRepository,
    val deleteFriendRepository: DeleteFriendRepository
)

fun Route.friends(dependencies: FriendsDependencies) = route("/friends") {
    addFriend(dependencies.addFriendRepository)
    getFriend(dependencies.getFriendsRepository)
    deleteFriend(dependencies.deleteFriendRepository)
}
