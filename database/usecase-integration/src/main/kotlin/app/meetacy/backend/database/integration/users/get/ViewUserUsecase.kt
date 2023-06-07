package app.meetacy.backend.database.integration.users.get

import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.users.get.ViewUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseViewUserStorage(db: Database): ViewUserUsecase.Storage {
    private val friendsTable = FriendsTable(db)

    override suspend fun isSubscriber(userId: UserId, subscriberId: UserId): Boolean =
        friendsTable.isSubscribed(userId, subscriberId)

}
