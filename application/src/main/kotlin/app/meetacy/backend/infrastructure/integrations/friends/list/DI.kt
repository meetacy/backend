@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.friends.list

import app.meetacy.backend.endpoint.friends.list.ListFriendsRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.friends.list.listFriendsStorage
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.usecase.integration.friends.get.UsecaseListFriendsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listFriendsRepository: ListFriendsRepository by Dependency

fun DIBuilder.listFriendsRepository() {
    val listFriendsRepository by singleton<ListFriendsRepository> {
        UsecaseListFriendsRepository(
            usecase = ListFriendsUsecase(
                authRepository = authRepository,
                getUsersViewsRepository = getUserViewsRepository,
                storage = listFriendsStorage
            )
        )
    }
}