package app.meetacy.backend.usecase.friends.list

import app.meetacy.backend.types.*
import app.meetacy.backend.usecase.types.*

class ListFriendsUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage,
    private val getUsersViewsRepository: GetUsersViewsRepository
) {
    suspend fun getFriendsUsecase(
        accessIdentity: AccessIdentity,
        amount: Amount,
        pagingId: PagingId?
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.InvalidToken }

        val friends = storage.getFriends(userId, amount, pagingId)

        val usersViewsIterator = getUsersViewsRepository
            .getUsersViews(userId, friends.map { (_, userId) -> userId })

        val paging = PagingResult(
            nextPagingId = if (friends.size == amount.int) friends.last().pagingId else null,
            data = usersViewsIterator
        )

        return Result.Success(paging)
    }

    sealed interface Result {
        object InvalidToken : Result
        class Success(val paging: PagingResult<List<UserView>>) : Result
    }

    interface Storage {
        suspend fun getFriends(
            userId: UserId,
            amount: Amount,
            pagingId: PagingId?
        ): List<FriendId>
    }

    data class FriendId(val pagingId: PagingId, val userId: UserId)
}
