package app.meetacy.backend.infrastructure.usecase.friends.add

import app.meetacy.backend.feature.friends.endpoints.add.AddFriendRepository
import app.meetacy.backend.infrastructure.database.friends.add.addFriendStorage
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.feature.friends.usecase.add.AddFriendUsecase
import app.meetacy.backend.feature.friends.usecase.integration.add.UsecaseAddFriendRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.addFriendRepository: AddFriendRepository by Dependency

fun DIBuilder.addFriendRepository() {
    val addFriendRepository by singleton<AddFriendRepository> {
        UsecaseAddFriendRepository(
            usecase = AddFriendUsecase(
                authRepository = get(),
                getUsersViewsRepository = getUserViewsRepository,
                storage = addFriendStorage
            )
        )
    }
}
