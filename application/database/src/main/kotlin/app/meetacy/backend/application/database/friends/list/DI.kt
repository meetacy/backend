package app.meetacy.backend.application.database.friends.list

import app.meetacy.backend.feature.friends.database.integration.friends.get.DatabaseGetFriendsStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.friends.usecase.list.ListFriendsUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listFriendsStorage: ListFriendsUsecase.Storage by Dependency

fun DIBuilder.listFriendsStorage() {
    val listFriendsStorage by singleton<ListFriendsUsecase.Storage> {
        DatabaseGetFriendsStorage(database)
    }
}
