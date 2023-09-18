package app.meetacy.backend.feature.friends.database.integration.friends.get

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.friends.usecase.list.ListFriendsUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.UserId
import org.jetbrains.exposed.sql.Database

class DatabaseGetFriendsStorage(db: Database) : ListFriendsUsecase.Storage {
    private val friendsStorage = FriendsStorage(db)

    override suspend fun getFriends(
        userId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<UserId> = friendsStorage.getFriends(userId, amount, pagingId)
}
