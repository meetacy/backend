package app.meetacy.backend.infrastructure.usecase.friends.list

import app.meetacy.backend.feature.friends.endpoints.list.ListFriendsRepository
import app.meetacy.backend.infrastructure.database.friends.list.listFriendsStorage
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.feature.friends.usecase.list.ListFriendsUsecase
import app.meetacy.backend.feature.friends.usecase.integration.get.UsecaseListFriendsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listFriendsRepository: ListFriendsRepository by Dependency

fun DIBuilder.listFriendsRepository() {
    val listFriendsRepository by singleton<ListFriendsRepository> {
        UsecaseListFriendsRepository(
            usecase = ListFriendsUsecase(
                authRepository = get(),
                getUsersViewsRepository = getUserViewsRepository,
                storage = listFriendsStorage
            )
        )
    }
}