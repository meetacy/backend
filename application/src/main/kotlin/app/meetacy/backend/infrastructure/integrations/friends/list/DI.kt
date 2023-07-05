@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.friends.list

import app.meetacy.backend.database.integration.friends.get.DatabaseGetFriendsStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.friends.list.ListFriendsRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.usecase.integration.friends.get.UsecaseListFriendsRepository

val DI.listFriendsRepository: ListFriendsRepository by Dependency

fun DIBuilder.listFriendsRepository() {
    val listFriendsRepository by singleton<ListFriendsRepository> {
        UsecaseListFriendsRepository(
            usecase = ListFriendsUsecase(
                authRepository = authRepository,
                getUsersViewsRepository = getUserViewsRepository,
                storage = DatabaseGetFriendsStorage(database)
            )
        )
    }
}