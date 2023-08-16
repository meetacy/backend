package app.meetacy.backend.feature.friends.usecase.delete

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserIdentity


class DeleteFriendUsecase(
    private val authRepository: AuthRepository,
    private val getUsersViewsRepository: GetUsersViewsRepository,
    private val storage: Storage
) {
    suspend fun deleteFriendUsecase(
        accessIdentity: AccessIdentity,
        friendIdentity: UserIdentity
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidToken }

        val friend = getUsersViewsRepository.getUsersViewsOrNull(userId, listOf(friendIdentity.id))
            .first()
            ?: return Result.FriendNotFound

        if (friend.identity.accessHash.string != friendIdentity.accessHash.string) return Result.FriendNotFound
        if (!storage.isSubscribed(userId, friendIdentity.id)) return Result.FriendNotFound

        storage.deleteFriend(userId, friendIdentity.id)
        return Result.Success
    }
    sealed interface Result {
        data object Success : Result
        data object InvalidToken : Result
        data object FriendNotFound : Result
    }
    interface Storage {
        suspend fun deleteFriend(
            userId: UserId,
            friendId: UserId
        )
        suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean
    }
}
