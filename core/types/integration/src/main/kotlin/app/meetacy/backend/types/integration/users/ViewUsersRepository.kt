package app.meetacy.backend.types.integration.users

import app.meetacy.backend.feature.users.usecase.get.ViewUsersUsecase
import app.meetacy.backend.types.users.ViewUsersRepository
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.viewUsersRepository() {
    val viewUsersRepository by singleton {
        val viewUsersUsecase: ViewUsersUsecase by getting
        ViewUsersRepository { viewerId, users ->
            viewUsersUsecase.viewUsers(viewerId, users)
        }
    }
}
