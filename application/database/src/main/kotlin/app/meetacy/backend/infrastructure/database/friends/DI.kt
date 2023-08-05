package app.meetacy.backend.infrastructure.database.friends

import app.meetacy.backend.database.friends.FriendsStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.friends.add.addFriend
import app.meetacy.backend.infrastructure.database.friends.delete.deleteFriend
import app.meetacy.backend.infrastructure.database.friends.list.listFriendsStorage
import app.meetacy.backend.infrastructure.database.friends.location.stream.locationStream
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.friendsStorage: FriendsStorage by Dependency

fun DIBuilder.friends() {
    addFriend()
    deleteFriend()
    listFriendsStorage()
    locationStream()
    val friendsStorage by singleton { FriendsStorage(database) }
}
