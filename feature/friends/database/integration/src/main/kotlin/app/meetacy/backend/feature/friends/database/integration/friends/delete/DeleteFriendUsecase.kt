package app.meetacy.backend.feature.friends.database.integration.friends.delete

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.friends.usecase.friends.delete.DeleteFriendUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseDeleteFriendStorage(db: Database) : DeleteFriendUsecase.Storage {
    private val friendsStorage = FriendsStorage(db)

    override suspend fun deleteFriend(userId: UserId, friendId: UserId) {
        friendsStorage.deleteFriend(userId, friendId)
    }

    override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean =
        friendsStorage.isSubscribed(userId, friendId)
}
