package app.meetacy.backend.database.integration.friends.get

import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.friends.get.GetFriendsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetFriendsStorage(db: Database) : GetFriendsUsecase.Storage {
    private val friendsTable = FriendsTable(db)

    override suspend fun getSubscriptions(userId: UserId): List<UserId> =
        friendsTable.getSubscriptions(userId)

    override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean =
        friendsTable.isSubscribed(userId, friendId)
}
