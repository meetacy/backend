package app.meetacy.backend.database.integration.notifications

import app.meetacy.backend.database.notifications.LastReadNotificationsTable
import app.meetacy.backend.database.notifications.NotificationsTable
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseReadNotificationsStorage(db: Database) : ReadNotificationsUsecase.Storage {
    private val notificationsTable = NotificationsTable(db)
    private val lastReadNotificationsTable = LastReadNotificationsTable(db)

    override suspend fun markAsRead(userId: UserId, lastNotificationId: NotificationId) {
        lastReadNotificationsTable.setLastReadNotificationId(userId, lastNotificationId)
    }

    override suspend fun notificationExists(notificationId: NotificationId): Boolean =
        notificationsTable.isNotificationExists(notificationId)

}
