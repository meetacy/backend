package app.meetacy.backend.feature.friends.database.integration

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

fun DIBuilder.friendsStorage() {
    val friendsStorage by singleton<FriendsStorage> {
        val database: Database by getting
        FriendsStorage(database)
    }
}
