package app.meetacy.backend.database.integration.users.get

import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.users.get.ViewUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetIsSubscriberStorage(db: Database) : ViewUserUsecase.IsSubscriberStorage {
    private val friendsTable = FriendsTable(db)

    override suspend fun isSubscriber(viewerId: UserId, expectedSubscriberId: UserId): Boolean =
        friendsTable.isSubscribed(userId = viewerId, friendId = expectedSubscriberId)

}