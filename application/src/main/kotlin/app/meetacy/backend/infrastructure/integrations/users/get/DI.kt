@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.users.get

import app.meetacy.backend.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.users.get.UserRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.usecase.integration.users.get.UsecaseUserRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.users.get.GetUserSafeUsecase

val DI.getUserRepository: UserRepository by Dependency
val DI.getUserViewsRepository: GetUsersViewsRepository by Dependency

fun DIBuilder.getUserRepository() {
    getUserViewsRepository()
    val getUserRepository by singleton<UserRepository> {
        UsecaseUserRepository(
            usecase = GetUserSafeUsecase(
                authRepository = authRepository,
                usersViewsRepository = getUserViewsRepository
            )
        )
    }
}

fun DIBuilder.getUserViewsRepository() {
    val getUserViewsRepository by singleton<GetUsersViewsRepository> {
        DatabaseGetUsersViewsRepository(database)
    }
}