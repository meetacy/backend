package app.meetacy.backend.usecase.friends.get

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.*

class GetFriendsUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getUsersViewsRepository: GetUsersViewsRepository
) {
    suspend fun getFriendsUsecase(
        accessToken: AccessToken
    ): Result {
        val userId = authRepository.authorize(accessToken) { return Result.InvalidToken }

        val (friends, subscriptions) = storage.getSubscriptions(userId)
            .partition { friendId -> storage.isSubscribed(friendId, userId) }

        val usersViewsIterator = getUsersViewsRepository
            .getUsersViews(userId, userIds = friends + subscriptions)
            .iterator()

        val friendsViews = friends.map { usersViewsIterator.next() }
        val subscriptionsViews = subscriptions.map { usersViewsIterator.next() }

        return Result.Success(friendsViews, subscriptionsViews)
    }

    sealed interface Result {
        object InvalidToken : Result
        class Success(val friends: List<UserView>, val subscriptions: List<UserView>) : Result
    }

    interface Storage{
        suspend fun getSubscriptions(
            userId: UserId
        ): List<UserId>
        suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean
    }
}
