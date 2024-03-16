package app.meetacy.backend.feature.friends.usecase.integration.subscribers

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.friends.usecase.subscribers.list.ListSubscribersUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.getSubscribersUsecase() {
    val listSubscribersUsecase by singleton<ListSubscribersUsecase> {
        val authRepository: AuthRepository by getting
        val getUsersViewsRepository: GetUsersViewsRepository by getting
        val storage = object : ListSubscribersUsecase.Storage {
            private val friendsStorage: FriendsStorage by getting

            override suspend fun getSubscribers(
                userId: UserId,
                amount: Amount,
                pagingId: PagingId?
            ): PagingResult<UserId> = friendsStorage.getSubscribers(
                userId,
                amount,
                pagingId
            )
        }

        ListSubscribersUsecase(storage, authRepository, getUsersViewsRepository)
    }
}
