package app.meetacy.backend.usecase.friends.add

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.types.authorize


class AddFriendUsecase(
    private val authRepository: AuthRepository,
    private val getUsersViewsRepository: GetUsersViewsRepository,
    private val storage: Storage
) {
    suspend fun addFriendUsecase(
        accessToken: AccessToken,
        friendId: UserId,
        friendAccessHash: AccessHash
    ): Result {
        val userId = authRepository.authorize(accessToken) { return Result.InvalidToken }
        val friend = getUsersViewsRepository.getUsersViewsOrNull(userId, listOf(friendId))
            .first()
            ?: return Result.FriendNotFound
        if (friend.accessHash != friendAccessHash) return Result.FriendNotFound

        storage.addFriend(userId, friendId)
        return Result.Success
    }
    sealed interface Result {
        object Success : Result
        object InvalidToken : Result
        object FriendNotFound : Result
    }
    interface Storage {
        suspend fun addFriend(
            userId: UserId,
            friendId: UserId
        )
    }
}
