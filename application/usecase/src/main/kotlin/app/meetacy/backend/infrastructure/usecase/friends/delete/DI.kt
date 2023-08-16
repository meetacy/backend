package app.meetacy.backend.infrastructure.usecase.friends.delete

import app.meetacy.backend.feature.friends.endpoints.delete.DeleteFriendRepository
import app.meetacy.backend.infrastructure.database.friends.delete.deleteFriendStorage
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.feature.friends.usecase.delete.DeleteFriendUsecase
import app.meetacy.backend.feature.friends.usecase.integration.delete.UsecaseDeleteFriendRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.deleteFriendRepository: DeleteFriendRepository by Dependency

fun DIBuilder.deleteFriendRepository() {
    val deleteFriendRepository by singleton<DeleteFriendRepository> {
        UsecaseDeleteFriendRepository(
            usecase = DeleteFriendUsecase(
                authRepository = get(),
                getUsersViewsRepository = getUserViewsRepository,
                storage = deleteFriendStorage
            )
        )
    }
}