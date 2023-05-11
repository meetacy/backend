package app.meetacy.backend.database.integration.location.stream

import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.location.stream.BaseFriendsLocationStreamingStorage
import org.jetbrains.exposed.sql.Database

class DatabaseFriendsLocationStreamingStorage(
    db: Database
) : BaseFriendsLocationStreamingStorage.Storage {
    private val friendsTable = FriendsTable(db)

    override suspend fun getFriends(userId: UserId, maxAmount: Amount): List<UserId> {
        return friendsTable.getFriends(
            userId = userId,
            amount = maxAmount,
            pagingId = null
        ).data
    }
}
