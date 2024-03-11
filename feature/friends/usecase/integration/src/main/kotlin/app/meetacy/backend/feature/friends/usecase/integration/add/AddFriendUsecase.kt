package app.meetacy.backend.feature.friends.usecase.integration.add

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.friends.usecase.add.AddFriendUsecase
import app.meetacy.backend.feature.notifications.usecase.add.AddNotificationUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.addFriendUsecase() {
    val addFriendUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getUsersViewsRepository: GetUsersViewsRepository by getting
        val addNotificationUsecase: AddNotificationUsecase by getting
        val friendsStorage: FriendsStorage by getting

        val storage = object : AddFriendUsecase.Storage {

            override suspend fun addFriend(userId: UserId, friendId: UserId) =
                friendsStorage.addFriend(userId, friendId)

            override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean =
                friendsStorage.isSubscribed(userId, friendId)

            override suspend fun addNotification(userId: UserId, subscriberId: UserId) =
                addNotificationUsecase.addSubscription(
                    userId = userId,
                    subscriberId = subscriberId,
                    date = DateTime.now()
                )
        }

        AddFriendUsecase(authRepository, getUsersViewsRepository, storage)
    }
}
