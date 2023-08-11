package app.meetacy.backend.feature.auth.usecase.friends.add

import app.meetacy.backend.feature.auth.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.feature.auth.usecase.types.authorizeWithUserId
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.user.UserIdentity


class AddFriendUsecase(
    private val authRepository: AuthRepository,
    private val getUsersViewsRepository: GetUsersViewsRepository,
    private val storage: Storage
) {
    suspend fun addFriendUsecase(
        accessIdentity: AccessIdentity,
        friendIdentity: UserIdentity
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidToken }

        val friend = getUsersViewsRepository.getUsersViewsOrNull(userId, listOf(friendIdentity.id))
            .first()
            ?: return Result.FriendNotFound

        if (friend.identity.accessHash.string != friendIdentity.accessHash.string) return Result.FriendNotFound

        if (storage.isSubscribed(userId, friendIdentity.id)) {
            return Result.FriendAlreadyAdded
        }

        storage.addFriend(userId, friendIdentity.id)
        storage.addNotification(friendIdentity.id, userId)

        return Result.Success
    }
    sealed interface Result {
        object Success : Result
        object InvalidToken : Result
        object FriendNotFound : Result
        object FriendAlreadyAdded : Result
    }
    interface Storage {
        suspend fun addFriend(
            userId: UserId,
            friendId: UserId
        )
        suspend fun addNotification(
            userId: UserId,
            subscriberId: UserId
        )
        suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean
    }
}
