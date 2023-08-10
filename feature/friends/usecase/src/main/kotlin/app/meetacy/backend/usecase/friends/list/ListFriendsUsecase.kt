package app.meetacy.backend.usecase.friends.list

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.UserId
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

        val friendIdsPaging = storage.getFriends(userId, amount, pagingId)

        val friendsPaging = friendIdsPaging.map { friendIds ->
            getUsersViewsRepository.getUsersViews(userId, friendIds)
        }

        return Result.Success(friendsPaging)
    }

    sealed interface Result {
        object InvalidToken : Result
        class Success(val paging: PagingResult<UserView>) : Result
    }

    interface Storage {
        suspend fun getFriends(
            userId: UserId,
            amount: Amount,
            pagingId: PagingId?
        ): PagingResult<UserId>
    }
}
