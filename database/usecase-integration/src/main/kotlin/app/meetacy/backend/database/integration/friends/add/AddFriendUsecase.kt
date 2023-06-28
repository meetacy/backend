package app.meetacy.backend.database.integration.friends.add

import app.meetacy.backend.database.friends.FriendsStorage
import app.meetacy.backend.database.notifications.NotificationsStorage
import app.meetacy.backend.database.types.DatabaseNotification
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseAddFriendStorage(db: Database) : AddFriendUsecase.Storage {
    private val friendsStorage = FriendsStorage(db)
    private val notificationsStorage = NotificationsStorage(db)

    override suspend fun addFriend(userId: UserId, friendId: UserId) =
        friendsStorage.addFriend(userId, friendId)

    override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean =
        friendsStorage.isSubscribed(userId, friendId)

    override suspend fun addNotification(userId: UserId, friendId: UserId) =
        notificationsStorage.addSubscriptionNotification(
            userId = userId,
            subscriberId = friendId,
            date = DateTime.now()
        )
}
