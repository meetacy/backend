package app.meetacy.backend.application.usecase.users.get

import app.meetacy.backend.feature.users.endpoints.get.UserRepository
import app.meetacy.backend.application.database.users.get.getUserViewsRepository
import app.meetacy.backend.feature.users.usecase.integration.users.get.UsecaseUserRepository
import app.meetacy.backend.feature.users.usecase.get.GetUserSafeUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.getUserRepository: UserRepository by Dependency

fun DIBuilder.getUserRepository() {
    val getUserRepository by singleton<UserRepository> {
        UsecaseUserRepository(
            usecase = GetUserSafeUsecase(
                authRepository = get(),
                usersViewsRepository = getUserViewsRepository
            )
        )
    }
}
