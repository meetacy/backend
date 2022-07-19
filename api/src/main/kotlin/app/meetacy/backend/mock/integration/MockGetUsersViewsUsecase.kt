package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.UserId
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.users.ViewUserUsecase
import app.meetacy.backend.usecase.users.GetUsersViewsUsecase

private object GetUsersViewsStorage : GetUsersViewsUsecase.Storage {
    override suspend fun getUsers(userIds: List<UserId>): List<FullUser?> =
        userIds.map { UsersStorage.getUser(it) }
            .map {  user ->
                user ?: return@map null
                with(user) {
                    FullUser(id, accessHash, nickname, email, emailVerified)
                }
            }
}

fun mockGetUsersViewsUsecase() = GetUsersViewsUsecase(GetUsersViewsStorage, ViewUserUsecase())
