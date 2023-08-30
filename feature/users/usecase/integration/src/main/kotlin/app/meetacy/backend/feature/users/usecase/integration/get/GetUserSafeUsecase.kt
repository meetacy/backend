package app.meetacy.backend.feature.users.usecase.integration.get

import app.meetacy.backend.feature.users.usecase.get.GetUserSafeUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.getUserSafeUsecase() {
    val getUserSafeUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getUsersViewsRepository: GetUsersViewsRepository by getting

        GetUserSafeUsecase(authRepository, getUsersViewsRepository)
    }
}
