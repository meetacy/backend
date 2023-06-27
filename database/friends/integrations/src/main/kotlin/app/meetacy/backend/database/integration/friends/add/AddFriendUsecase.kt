package app.meetacy.backend.database.integration.friends.add

import app.meetacy.backend.database.friends.FriendsStorage
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseAddFriendStorage(db: Database) : AddFriendUsecase.Storage {
    private val friendsStorage = FriendsStorage(db)

    override suspend fun addFriend(userId: UserId, friendId: UserId) =
        friendsStorage.addFriend(userId, friendId)

    override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean =
        friendsStorage.isSubscribed(userId, friendId)
}
