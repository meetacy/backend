@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.friends

import app.meetacy.backend.database.friends.FriendsStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.infrastructure.database.database

val DI.friendsStorage: FriendsStorage by Dependency

fun DIBuilder.friends() {
    val friendsStorage by singleton { FriendsStorage(database) }
}
