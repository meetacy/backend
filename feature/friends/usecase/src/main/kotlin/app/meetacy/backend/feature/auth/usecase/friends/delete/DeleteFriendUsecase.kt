package app.meetacy.backend.feature.auth.usecase.friends.delete

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.user.UserIdentity
import app.meetacy.backend.feature.auth.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.feature.auth.usecase.types.authorizeWithUserId


class DeleteFriendUsecase(
    private val authRepository: app.meetacy.backend.feature.auth.usecase.types.AuthRepository,
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
        object Success : Result
        object InvalidToken : Result
        object FriendNotFound : Result
    }
    interface Storage {
        suspend fun deleteFriend(
            userId: UserId,
            friendId: UserId
        )
        suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean
    }
}
