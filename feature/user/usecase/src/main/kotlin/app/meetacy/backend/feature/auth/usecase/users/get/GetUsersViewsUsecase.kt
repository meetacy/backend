package app.meetacy.backend.feature.auth.usecase.users.get

import app.meetacy.backend.feature.auth.usecase.types.FullUser
import app.meetacy.backend.feature.auth.usecase.types.UserView
import app.meetacy.backend.types.user.UserId

class GetUsersViewsUsecase(
    private val storage: Storage,
    private val viewUserRepository: ViewUserRepository
) {
    suspend fun viewUsers(viewerId: UserId, userIdentities: List<UserId>): List<UserView?> =
        storage.getUsers(userIdentities)
            .map { user ->
                user ?: return@map null
                // fixme: create batch request instead of converting
                //  one by one
                viewUserRepository.viewUser(viewerId, user)
            }

    interface Storage {
        suspend fun getUsers(userIdentities: List<UserId>): List<FullUser?>
    }

    interface ViewUserRepository {
        suspend fun viewUser(viewerId: UserId, user: FullUser): UserView
    }
}
