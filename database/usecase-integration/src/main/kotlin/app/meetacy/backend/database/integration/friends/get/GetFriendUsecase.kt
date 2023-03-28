package app.meetacy.backend.database.integration.friends.get

import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.types.Amount
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetFriendsStorage(db: Database) : ListFriendsUsecase.Storage {
    private val friendsTable = FriendsTable(db)

    override suspend fun getFriends(
        userId: UserId,
        amount: Amount,
        lastUserId: UserId?
    ): List<ListFriendsUsecase.FriendId> = friendsTable.getFriends(userId, amount, lastUserId)
        .map { (pagingId, userId) -> ListFriendsUsecase.FriendId(pagingId, userId) }
}
