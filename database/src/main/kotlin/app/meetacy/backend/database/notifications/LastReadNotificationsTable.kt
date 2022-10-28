@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.notifications

import app.meetacy.backend.types.NotificationId
import app.meetacy.backend.types.UserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class LastReadNotificationsTable(private val db: Database) : Table() {
    private val USER_ID = long("USER_ID")
    private val NOTIFICATION_ID = long("NOTIFICATION_ID")

    init {
        transaction(db) {
            SchemaUtils.create(this@LastReadNotificationsTable)
        }
    }

    suspend fun setLastReadNotificationId(userId: UserId, notificationId: NotificationId) =
        newSuspendedTransaction(db = db) {
            insert { statement ->
                statement[USER_ID] = userId.long
                statement[NOTIFICATION_ID] = notificationId.long
            }
        }

    suspend fun getLastReadNotificationId(userId: UserId): NotificationId {
        val result = newSuspendedTransaction(db = db) {
            select { (USER_ID eq userId.long) }
                .firstOrNull()
        }
        return if (result != null) NotificationId(result[NOTIFICATION_ID]) else NotificationId(0)
    }
}