package app.meetacy.backend.usecase.users

import app.meetacy.backend.domain.UserId
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.UserView

class GetUsersViewsUsecase(
    private val storage: Storage,
    private val viewUserUsecase: ViewUserUsecase
) {
    suspend fun viewUsers(viewerId: UserId, userIds: List<UserId>): List<UserView?> =
        storage.getUsers(userIds)
            .map { user ->
                user ?: return@map null
                viewUserUsecase.viewUser(viewerId, user)
            }

    interface Storage {
        suspend fun getUsers(userIds: List<UserId>): List<FullUser?>
    }
}
