package app.meetacy.backend.infrastructure.integrations.friends

import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.friends.FriendsDependencies
import app.meetacy.backend.infrastructure.integrations.friends.add.addFriendRepository
import app.meetacy.backend.infrastructure.integrations.friends.delete.deleteFriendRepository
import app.meetacy.backend.infrastructure.integrations.friends.list.listFriendsRepository
import app.meetacy.backend.infrastructure.integrations.friends.location.stream.friendLocationDependencies
import app.meetacy.backend.infrastructure.integrations.friends.location.stream.locationStreamDependencies

val DI.friendsDependencies: FriendsDependencies by Dependency

fun DIBuilder.friends() {
    addFriendRepository()
    deleteFriendRepository()
    listFriendsRepository()
    locationStreamDependencies()
    val friendsDependencies by singleton<FriendsDependencies> {
        FriendsDependencies(
            friendLocationDependencies,
            addFriendRepository,
            listFriendsRepository,
            deleteFriendRepository
        )
    }
}
