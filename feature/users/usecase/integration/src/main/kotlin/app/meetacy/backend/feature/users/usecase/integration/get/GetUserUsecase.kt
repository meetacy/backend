package app.meetacy.backend.feature.users.usecase.integration.get

import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.feature.users.usecase.get.GetUsersViewsUsecase
import app.meetacy.backend.feature.users.usecase.get.ViewUserUsecase
import app.meetacy.backend.types.users.FullUser
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserView
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.getUsersViewsUsecase() {
    val getUsersViewsUsecase by singleton {
        val usersStorage: UsersStorage by getting
        val viewUserUsecase: ViewUserUsecase by getting

        val storage = object : GetUsersViewsUsecase.Storage {
            override suspend fun getUsers(
                userIdentities: List<UserId>
            ): List<FullUser?> = usersStorage.getUsersOrNull(userIdentities)
        }

        val viewUserRepository = object : GetUsersViewsUsecase.ViewUserRepository {
            override suspend fun viewUser(
                viewerId: UserId,
                user: FullUser
            ): UserView = viewUserUsecase.viewUser(viewerId, user)
        }

        GetUsersViewsUsecase(
            storage = storage,
            viewUserRepository = viewUserRepository
        )
    }
}
