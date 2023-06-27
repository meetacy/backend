package app.meetacy.backend.database.integration.location.stream

import app.meetacy.backend.database.friends.FriendsStorage
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.location.stream.BaseFriendsLocationStreamingStorage
import org.jetbrains.exposed.sql.Database

class DatabaseFriendsLocationStreamingStorage(
    db: Database
) : BaseFriendsLocationStreamingStorage.Storage {
    private val friendsStorage = FriendsStorage(db)

    override suspend fun getFriends(userId: UserId, maxAmount: Amount): List<UserId> {
        return friendsStorage.getFriends(
            userId = userId,
            amount = maxAmount,
            pagingId = null
        ).data
    }
}
