package app.meetacy.backend.database.integration.users.get

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.usecase.users.get.ViewUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseViewUserStorage(db: Database): ViewUserUsecase.Storage {
    private val friendsStorage = FriendsStorage(db)

    override suspend fun isSubscriber(userId: UserId, subscriberId: UserId): Boolean =
        friendsStorage.isSubscribed(userId, subscriberId)

}
