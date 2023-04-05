package app.meetacy.backend.database.integration.friends.get

import app.meetacy.backend.database.friends.FriendsTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetFriendsStorage(db: Database) : ListFriendsUsecase.Storage {
    private val friendsTable = FriendsTable(db)

    override suspend fun getFriends(
        userId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<List<UserId>> = friendsTable.getFriends(userId, amount, pagingId)
}
