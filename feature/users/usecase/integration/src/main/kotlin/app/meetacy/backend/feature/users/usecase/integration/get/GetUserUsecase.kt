package app.meetacy.backend.feature.users.usecase.integration.get

import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.feature.users.usecase.get.GetUsersViewsUsecase
import app.meetacy.backend.feature.users.usecase.get.ViewUsersUsecase
import app.meetacy.backend.types.users.FullUser
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserView
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.getUsersViewsUsecase() {
    val getUsersViewsUsecase by singleton {
        val usersStorage: UsersStorage by getting
        val viewUsersUsecase: ViewUsersUsecase by getting

        val storage = object : GetUsersViewsUsecase.Storage {
            override suspend fun getUsers(
                userIdentities: List<UserId>
            ): List<FullUser?> = usersStorage.getUsersOrNull(userIdentities)
        }

        val viewUserRepository = object : GetUsersViewsUsecase.ViewUserRepository {
            override suspend fun viewUsers(
                viewerId: UserId, users: List<FullUser>
            ): List<UserView> = viewUsersUsecase.viewUsers(viewerId, users)
        }

        GetUsersViewsUsecase(
            storage = storage,
            viewUserRepository = viewUserRepository
        )
    }
}
