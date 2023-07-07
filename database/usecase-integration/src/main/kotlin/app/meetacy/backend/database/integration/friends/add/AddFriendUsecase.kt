package app.meetacy.backend.database.integration.friends.add

import app.meetacy.backend.database.friends.FriendsStorage
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import app.meetacy.backend.usecase.notifications.add.AddNotificationUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseAddFriendStorage(
    db: Database,
    private val addNotificationUsecase: AddNotificationUsecase
) : AddFriendUsecase.Storage {
    private val friendsStorage = FriendsStorage(db)

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
