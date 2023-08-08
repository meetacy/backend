package app.meetacy.backend.infrastructure.usecase.friends.add

import app.meetacy.backend.endpoint.friends.add.AddFriendRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.friends.add.addFriendStorage
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.integration.friends.add.UsecaseAddFriendRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.addFriendRepository: AddFriendRepository by Dependency

fun DIBuilder.addFriendRepository() {
    val addFriendRepository by singleton<AddFriendRepository> {
        UsecaseAddFriendRepository(
            usecase = AddFriendUsecase(
                authRepository = authRepository,
                getUsersViewsRepository = getUserViewsRepository,
                storage = addFriendStorage
            )
        )
    }
}
