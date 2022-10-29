package app.meetacy.backend.database.integration.friends.add

import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseAddFriendStorage(db: Database) : AddFriendUsecase.Storage {
    private val friendsTable = FriendsTable(db)

    override suspend fun addFriend(userId: UserId, friendId: UserId) =
        friendsTable.addFriend(userId, friendId)

    override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean =
        friendsTable.isSubscribed(userId, friendId)
}
