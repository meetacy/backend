package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.integration.types.mapToUsecase
import app.meetacy.backend.types.UserId
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.usecase.users.GetUsersViewsUsecase
import app.meetacy.backend.usecase.users.ViewUserUsecase

object MockGetUsersViewsStorage : GetUsersViewsUsecase.Storage {
    override suspend fun getUsers(userIds: List<UserId>): List<FullUser?> =
        userIds
            .map { UsersStorage.getUser(it) }
            .map {  user -> user?.mapToUsecase() }
}

object MockGetUsersViewsViewUserRepository : GetUsersViewsUsecase.ViewUserRepository {
    override suspend fun viewUser(viewerId: UserId, user: FullUser): UserView =
        ViewUserUsecase().viewUser(viewerId, user)
}
