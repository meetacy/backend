@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.feature.friends.database.friends

import app.meetacy.backend.feature.friends.database.friends.FriendsTable.FRIEND_ID
import app.meetacy.backend.feature.friends.database.friends.FriendsTable.ID
import app.meetacy.backend.feature.friends.database.friends.FriendsTable.USER_ID
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.pagingIdLong
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object FriendsTable : Table() {
    val ID = long("PAGING_ID").autoIncrement()
    val USER_ID = long("USER_ID")
    val FRIEND_ID = long("FRIEND_ID")

    override val primaryKey = PrimaryKey(ID)
}

class FriendsStorage(private val db: Database) {

    suspend fun addFriend(userId: UserId, friendId: UserId) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            FriendsTable.insert { statement ->
                statement[USER_ID] = userId.long
                statement[FRIEND_ID] = friendId.long
            }
        }
    }

    suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean = newSuspendedTransaction(Dispatchers.IO, db) {
        FriendsTable.select {
            (USER_ID eq userId.long) and (FRIEND_ID eq friendId.long)
        }.any()
    }

    data class IsSubscriber(
        val userId: UserId,
        val subscriberId: UserId
    )

    suspend fun isSubscribers(users: List<IsSubscriber>): List<Boolean> = newSuspendedTransaction(Dispatchers.IO, db) {
        val results = FriendsTable.select {
            users.fold(Op.TRUE) { acc: Op<Boolean>, (subscriberId, userId) ->
                acc or ((USER_ID eq subscriberId.long) and (FRIEND_ID eq userId.long))
            }
        }
        users.map { (subscriberId, userId) ->
            results.any { result ->
                result[USER_ID] == subscriberId.long && result[FRIEND_ID] == userId.long
            }
        }
    }

    suspend fun getFriends(
        userId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<UserId> {
        val results = newSuspendedTransaction(Dispatchers.IO, db) {
            FriendsTable.select { (USER_ID eq userId.long) and (ID less (pagingId?.long ?: Long.MAX_VALUE)) }
                .orderBy(ID, SortOrder.DESC)
                .asFlow()
                .filter { row -> isSubscribed(UserId(row[FRIEND_ID]), UserId(row[USER_ID])) }
                .take(amount.int)
                .toList()
        }

        return PagingResult(
            data = results.map { result -> UserId(result[FRIEND_ID]) },
            nextPagingId = results.pagingIdLong(amount) { it[ID] }
        )
    }

    suspend fun getSubscriptions(userId: UserId): List<UserId> = newSuspendedTransaction(Dispatchers.IO, db) {
        FriendsTable.select { (USER_ID eq userId.long) }
            .map { result -> UserId(result[FRIEND_ID]) }
    }

    suspend fun deleteFriend(userId: UserId, friendId: UserId) =
        newSuspendedTransaction(Dispatchers.IO, db) {
            FriendsTable.deleteWhere { ((USER_ID eq userId.long) and (FRIEND_ID eq friendId.long)) }
        }
}
