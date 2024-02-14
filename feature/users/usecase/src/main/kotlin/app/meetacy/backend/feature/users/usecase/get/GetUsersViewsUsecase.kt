package app.meetacy.backend.feature.users.usecase.get

import app.meetacy.backend.types.users.FullUser
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserView

class GetUsersViewsUsecase(
    private val storage: Storage,
    private val viewUserRepository: ViewUserRepository
) {
    suspend fun viewUsers(viewerId: UserId, userIds: List<UserId>): List<UserView?> {
        val fullUsers = storage.getUsers(userIds)
        return viewUserRepository.viewUsers(viewerId, fullUsers.filterNotNull())
    }

    interface Storage {
        suspend fun getUsers(userIdentities: List<UserId>): List<FullUser?>
    }

    interface ViewUserRepository {
        suspend fun viewUsers(viewerId: UserId, users: List<FullUser>): List<UserView>
    }
}
