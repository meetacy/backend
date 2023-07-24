@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.friends.add

import app.meetacy.backend.database.integration.friends.add.DatabaseAddFriendStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.friends.add.AddFriendRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.notifications.add.addNotificationUsecase
import app.meetacy.backend.infrastructure.integrations.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.integration.friends.add.UsecaseAddFriendRepository

val DI.addFriendRepository: AddFriendRepository by Dependency

fun DIBuilder.addFriendRepository() {
    val addFriendRepository by singleton<AddFriendRepository> {
        UsecaseAddFriendRepository(
            usecase = AddFriendUsecase(
                authRepository = authRepository,
                getUsersViewsRepository = getUserViewsRepository,
                storage = DatabaseAddFriendStorage(database, addNotificationUsecase)
            )
        )
    }
}
