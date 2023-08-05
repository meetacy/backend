@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.users.get

import app.meetacy.backend.endpoint.users.get.UserRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.users.get.getUserViewsRepository
import app.meetacy.backend.usecase.integration.users.get.UsecaseUserRepository
import app.meetacy.backend.usecase.users.get.GetUserSafeUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getUserRepository: UserRepository by Dependency

fun DIBuilder.getUserRepository() {
    val getUserRepository by singleton<UserRepository> {
        UsecaseUserRepository(
            usecase = GetUserSafeUsecase(
                authRepository = authRepository,
                usersViewsRepository = getUserViewsRepository
            )
        )
    }
}
