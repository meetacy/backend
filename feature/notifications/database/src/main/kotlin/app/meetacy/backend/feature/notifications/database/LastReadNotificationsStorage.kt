@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.feature.notifications.database

import app.meetacy.backend.feature.notifications.database.LastReadNotificationsTable.NOTIFICATION_ID
import app.meetacy.backend.feature.notifications.database.LastReadNotificationsTable.USER_ID
import app.meetacy.backend.feature.users.database.users.UsersTable
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object LastReadNotificationsTable : Table() {
    val USER_ID = reference("USER_ID", UsersTable.USER_ID)
    val NOTIFICATION_ID = reference("NOTIFICATION_ID", NotificationsTable.NOTIFICATION_ID)
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
