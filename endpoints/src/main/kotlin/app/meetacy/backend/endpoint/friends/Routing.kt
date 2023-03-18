package app.meetacy.backend.endpoint.friends

import app.meetacy.backend.endpoint.friends.add.AddFriendRepository
import app.meetacy.backend.endpoint.friends.add.addFriend
import app.meetacy.backend.endpoint.friends.delete.DeleteFriendRepository
import app.meetacy.backend.endpoint.friends.delete.deleteFriend
import app.meetacy.backend.endpoint.friends.list.ListFriendsRepository
import app.meetacy.backend.endpoint.friends.list.listFriends
import io.ktor.server.routing.*

class FriendsDependencies(
    val addFriendRepository: AddFriendRepository,
    val listFriendsRepository: ListFriendsRepository,
    val deleteFriendRepository: DeleteFriendRepository
)

fun Route.friends(dependencies: FriendsDependencies) = route("/friends") {
    addFriend(dependencies.addFriendRepository)
    listFriends(dependencies.listFriendsRepository)
    deleteFriend(dependencies.deleteFriendRepository)
}
