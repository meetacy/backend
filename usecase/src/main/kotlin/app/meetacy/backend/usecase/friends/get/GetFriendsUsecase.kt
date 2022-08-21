package app.meetacy.backend.usecase.friends.get

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.FriendsAndSubscriptions
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
        val friendsAndSubscriptions = storage
            .getFriendsAndSubscriptions(userId)

        val friendsViews = getUsersViewsRepository.getUsersViews(userId, friendsAndSubscriptions.friends)
        val subscriptionsViews = getUsersViewsRepository.getUsersViews(userId, friendsAndSubscriptions.subscriptions)

        return Result.Success(friendsViews, subscriptionsViews)
    }

    sealed interface Result {
        object InvalidToken : Result
        class Success(val friends: List<UserView>, val subscriptions: List<UserView>) : Result
    }

    interface Storage{
        fun getFriendsAndSubscriptions(
            userId: UserId
        ): FriendsAndSubscriptions
    }
}
