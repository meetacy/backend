@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.friends

import app.meetacy.backend.database.types.DatabaseFriend
import app.meetacy.backend.types.Amount
import app.meetacy.backend.types.PagingId
import app.meetacy.backend.types.UserId
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class FriendsTable(private val db: Database) : Table()  {
    private val PAGING_ID = long("PAGING_ID").autoIncrement()
    private val USER_ID = long("USER_ID")
    private val FRIEND_ID = long("FRIEND_ID")

    override val primaryKey = PrimaryKey(PAGING_ID)

    init {
        transaction(db) {
            SchemaUtils.create(this@FriendsTable)
        }
    }

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
    ): List<DatabaseFriend> = newSuspendedTransaction(db = db) {
        select { (USER_ID eq userId.long) and (PAGING_ID less (pagingId?.long ?: Long.MAX_VALUE)) }
            .orderBy(PAGING_ID, SortOrder.DESC)
            .asFlow()
            .map { result ->
                DatabaseFriend(
                    pagingId = PagingId(result[PAGING_ID]),
                    friendId = UserId(result[FRIEND_ID])
                ).also(::println)
            }.filter { (_, friendId) -> isSubscribed(friendId, userId).also(::println) }.take(amount.int)
            .toList()
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
