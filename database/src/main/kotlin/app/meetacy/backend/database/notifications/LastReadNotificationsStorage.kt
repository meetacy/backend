@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.notifications

import app.meetacy.backend.database.notifications.LastReadNotificationsTable.NOTIFICATION_ID
import app.meetacy.backend.database.notifications.LastReadNotificationsTable.USER_ID
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object LastReadNotificationsTable : Table() {
    val USER_ID = long("USER_ID")
    val NOTIFICATION_ID = long("NOTIFICATION_ID")
}

class LastReadNotificationsStorage(private val db: Database) {

    suspend fun setLastReadNotificationId(userId: UserId, notificationId: NotificationId) =
        newSuspendedTransaction(Dispatchers.IO, db) {
           LastReadNotificationsTable.insert { statement ->
                statement[USER_ID] = userId.long
                statement[NOTIFICATION_ID] = notificationId.long
            }
        }

    suspend fun getLastReadNotificationId(userId: UserId): NotificationId {
        val result = newSuspendedTransaction(Dispatchers.IO, db) {
            LastReadNotificationsTable.select { (USER_ID eq userId.long) }
                .firstOrNull()
        }
        return if (result != null) NotificationId(result[NOTIFICATION_ID]) else NotificationId(0)
    }
}