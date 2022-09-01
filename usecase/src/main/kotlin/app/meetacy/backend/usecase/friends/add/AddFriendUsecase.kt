package app.meetacy.backend.usecase.friends.add

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.types.authorizeWithUserId


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

        val friend = getUsersViewsRepository.getUsersViewsOrNull(userId, listOf(friendIdentity.userId))
            .first()
            ?: return Result.FriendNotFound

        if (friend.identity.accessHash.string != friendIdentity.accessHash.string) {
            println(friend.identity.accessHash.string)
            println(friendIdentity.accessHash.string)
            return Result.FriendNotFound
        }


        if (!storage.isSubscribed(userId, friendIdentity.userId)) storage.addFriend(userId, friendIdentity.userId)
            else return Result.FriendAlreadyAdded

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
        suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean
    }
}
