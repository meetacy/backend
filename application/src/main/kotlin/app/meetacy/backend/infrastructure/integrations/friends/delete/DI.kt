@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.friends.delete

import app.meetacy.backend.database.integration.friends.delete.DatabaseDeleteFriendStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.friends.delete.DeleteFriendRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.usecase.integration.friends.delete.UsecaseDeleteFriendRepository

val DI.deleteFriendRepository: DeleteFriendRepository by Dependency

fun DIBuilder.deleteFriendRepository() {
    val deleteFriendRepository by singleton<DeleteFriendRepository> {
        UsecaseDeleteFriendRepository(
            usecase = DeleteFriendUsecase(
                authRepository = authRepository,
                getUsersViewsRepository = getUserViewsRepository,
                storage = DatabaseDeleteFriendStorage(database)
            )
        )
    }
}