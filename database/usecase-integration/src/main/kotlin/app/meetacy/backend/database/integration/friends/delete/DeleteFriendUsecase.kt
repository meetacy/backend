package app.meetacy.backend.database.integration.friends.delete

import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.friends.delete.DeleteFriendUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseDeleteFriendStorage(db: Database) : DeleteFriendUsecase.Storage {
    private val friendsTable = FriendsTable(db)

    override suspend fun deleteFriend(userId: UserId, friendId: UserId) {
        friendsTable.deleteFriend(userId, friendId)
    }

    override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean =
        friendsTable.isSubscribed(userId, friendId)
}
