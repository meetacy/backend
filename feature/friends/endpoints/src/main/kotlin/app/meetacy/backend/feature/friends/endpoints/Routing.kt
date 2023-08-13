package app.meetacy.backend.feature.friends.endpoints

import app.meetacy.backend.feature.friends.endpoints.add.AddFriendRepository
import app.meetacy.backend.feature.friends.endpoints.add.addFriend
import app.meetacy.backend.feature.friends.endpoints.delete.DeleteFriendRepository
import app.meetacy.backend.feature.friends.endpoints.delete.deleteFriend
import app.meetacy.backend.feature.friends.endpoints.list.ListFriendsRepository
import app.meetacy.backend.feature.friends.endpoints.list.listFriends
import app.meetacy.backend.feature.friends.endpoints.location.FriendsLocationDependencies
import app.meetacy.backend.feature.friends.endpoints.location.friendsLocation
import io.ktor.server.routing.*

class FriendsDependencies(
    val friendsLocationDependencies: FriendsLocationDependencies,
    val addFriendRepository: AddFriendRepository,
    val listFriendsRepository: ListFriendsRepository,
    val deleteFriendRepository: DeleteFriendRepository
)

fun Route.friends(dependencies: FriendsDependencies) = route("/friends") {
    friendsLocation(dependencies.friendsLocationDependencies)

    addFriend(dependencies.addFriendRepository)
    listFriends(dependencies.listFriendsRepository)
    deleteFriend(dependencies.deleteFriendRepository)
}
