@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.friends

import app.meetacy.backend.types.UserId
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class FriendsTable(private val db: Database) : Table()  {
    private val USER_ID = long("USER_ID")
    private val FRIEND_ID = long("FRIEND_ID")

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

    suspend fun getSubscriptions(userId: UserId): List<UserId> = newSuspendedTransaction(db = db) {
        select { (USER_ID eq userId.long) }
            .map { result -> UserId(result[FRIEND_ID]) }
    }
}
