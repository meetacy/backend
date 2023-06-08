package app.meetacy.backend.database.integration.friends.get

import app.meetacy.backend.database.friends.FriendsStorage
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetFriendsStorage(db: Database) : ListFriendsUsecase.Storage {
    private val friendsStorage = FriendsStorage(db)

    override suspend fun getFriends(
        userId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<UserId> = friendsStorage.getFriends(userId, amount, pagingId)
}
