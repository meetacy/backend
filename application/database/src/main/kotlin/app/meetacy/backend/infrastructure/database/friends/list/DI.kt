package app.meetacy.backend.infrastructure.database.friends.list

import app.meetacy.backend.database.integration.friends.get.DatabaseGetFriendsStorage
import app.meetacy.backend.feature.auth.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listFriendsStorage: ListFriendsUsecase.Storage by Dependency

fun DIBuilder.listFriendsStorage() {
    val listFriendsStorage by singleton<ListFriendsUsecase.Storage> {
        DatabaseGetFriendsStorage(database)
    }
}
