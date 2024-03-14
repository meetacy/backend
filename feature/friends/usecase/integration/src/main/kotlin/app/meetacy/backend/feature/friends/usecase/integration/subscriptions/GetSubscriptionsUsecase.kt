package app.meetacy.backend.feature.friends.usecase.integration.subscriptions

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.friends.usecase.subscriptions.GetSubscriptionsUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.getSubscriptionsUsecase() {
    val getSubscriptionsUsecase by singleton<GetSubscriptionsUsecase> {
        val authRepository: AuthRepository by getting
        val getUsersViewsRepository: GetUsersViewsRepository by getting
        val storage = object : GetSubscriptionsUsecase.Storage {
            private val friendsStorage: FriendsStorage by getting

            override suspend fun getSubscriptions(
                userId: UserId,
                amount: Amount,
                pagingId: PagingId?
            ): PagingResult<UserId> = friendsStorage.getSubscriptions(
                userId,
                amount,
                pagingId
            )

            override suspend fun getCountSubscribers(userId: UserId): Amount.OrZero{
                return friendsStorage.getSubscriptionsAmount(userId)
            }

            override suspend fun getCountSubscriptions(userId: UserId): Amount.OrZero {
                return friendsStorage.getSubscriptionsAmount(userId)
            }
        }

        GetSubscriptionsUsecase(storage, authRepository, getUsersViewsRepository)
    }
}
