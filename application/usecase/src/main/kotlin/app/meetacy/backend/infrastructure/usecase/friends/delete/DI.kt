package app.meetacy.backend.infrastructure.usecase.friends.delete

import app.meetacy.backend.endpoint.friends.delete.DeleteFriendRepository
import app.meetacy.backend.feature.auth.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.feature.auth.usecase.integration.friends.delete.UsecaseDeleteFriendRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.friends.delete.deleteFriendStorage
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.deleteFriendRepository: DeleteFriendRepository by Dependency

fun DIBuilder.deleteFriendRepository() {
    val deleteFriendRepository by singleton<DeleteFriendRepository> {
        UsecaseDeleteFriendRepository(
            usecase = DeleteFriendUsecase(
                authRepository = authRepository,
                getUsersViewsRepository = getUserViewsRepository,
                storage = deleteFriendStorage
            )
        )
    }
}