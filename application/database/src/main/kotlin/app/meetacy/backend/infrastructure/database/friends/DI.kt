@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.database.friends

import app.meetacy.backend.database.friends.FriendsStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.friendsStorage: FriendsStorage by Dependency

fun DIBuilder.friends() {
    val friendsStorage by singleton { FriendsStorage(database) }
}
