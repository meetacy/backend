package app.meetacy.backend.usecase.friends.get

import app.meetacy.backend.types.AccessToken
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
        val friends = storage.getFriends(userId)
        val viewFriends = getUsersViewsRepository.getUsersViewsOrNull(userId, friends)
        val subscriptions = storage.getSubscriptions(userId)
        return Result.Success(friends,subscriptions)
    }

    sealed interface Result {
        object InvalidToken : Result
        class Success(val friends: List<UserView>, val subscriptions: List<UserView>) : Result
    }

    interface Storage{
        fun getFriends(
            userId: UserId
        ): List<FullUser>
        fun getSubscriptions(
            userId: UserId
        ): List<FullUser>
    }
}
