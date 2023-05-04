@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.friends

import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class FriendsTable(private val db: Database) : Table()  {
    private val ID = long("PAGING_ID").autoIncrement()
    private val USER_ID = long("USER_ID")
    private val FRIEND_ID = long("FRIEND_ID")

    override val primaryKey = PrimaryKey(ID)

    init {
        transaction(db) {
            SchemaUtils.create(this@FriendsTable)
        }
    }

    // bpavuk's proposal: rename this function to "subscribeOn" for the sake of clarity
    suspend fun addFriend(userId: UserId, friendId: UserId) {
        newSuspendedTransaction(db = db) {
            insert { statement ->
                statement[USER_ID] = userId.long
                statement[FRIEND_ID] = friendId.long
            }
        }
    }

    suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean = newSuspendedTransaction(db = db) {
        select {
            (USER_ID eq userId.long) and (FRIEND_ID eq friendId.long)
        }.any()
    }

    suspend fun getFriends(
        userId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<List<UserId>> {
        val results = newSuspendedTransaction(db = db) {
            select { (USER_ID eq userId.long) and (ID less (pagingId?.long ?: Long.MAX_VALUE)) }
                .orderBy(ID, SortOrder.DESC)
                .asFlow()
                .filter { row -> isSubscribed(UserId(row[FRIEND_ID]), UserId(row[USER_ID])) }
                .take(amount.int)
                .toList()
        }

        val nextPagingId = if (results.size == amount.int) PagingId(results.last()[ID]) else null

        return PagingResult(
            data = results.map { result -> UserId(result[FRIEND_ID]) },
            nextPagingId = nextPagingId
        )
    }

    suspend fun getSubscriptions(userId: UserId): List<UserId> = newSuspendedTransaction(db = db) {
        select { (USER_ID eq userId.long) }
            .map { result -> UserId(result[FRIEND_ID]) }
    }

    suspend fun deleteFriend(userId: UserId, friendId: UserId) =
        newSuspendedTransaction(db = db) {
            deleteWhere { ((USER_ID eq userId.long) and (FRIEND_ID eq friendId.long)) }
        }
}
