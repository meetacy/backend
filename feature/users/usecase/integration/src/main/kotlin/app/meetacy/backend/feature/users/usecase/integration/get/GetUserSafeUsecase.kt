package app.meetacy.backend.feature.users.usecase.integration.get

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.users.usecase.get.GetUserSafeUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.getUserSafeUsecase() {
    val getUserSafeUsecase by singleton {
        val friendsStorage: FriendsStorage by getting

        val storage = object : GetUserSafeUsecase.Storage {
            override suspend fun getSubscribers(userId: UserId): Amount.OrZero {
                return friendsStorage.getSubscribersAmount(userId)
            }
            override suspend fun getSubscriptions(userId: UserId): Amount.OrZero {
                return friendsStorage.getSubscriptionsAmount(userId)
            }
        }

        val authRepository: AuthRepository by getting
        val getUsersViewsRepository: GetUsersViewsRepository by getting

        GetUserSafeUsecase(storage, authRepository, getUsersViewsRepository)
    }
}
