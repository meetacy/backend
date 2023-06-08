package app.meetacy.backend.database.integration.notifications

import app.meetacy.backend.database.notifications.LastReadNotificationsStorage
import app.meetacy.backend.database.notifications.NotificationsStorage
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseReadNotificationsStorage(db: Database) : ReadNotificationsUsecase.Storage {
    private val notificationsStorage = NotificationsStorage(db)
    private val lastReadNotificationsStorage = LastReadNotificationsStorage(db)

    override suspend fun markAsRead(userId: UserId, lastNotificationId: NotificationId) {
        lastReadNotificationsStorage.setLastReadNotificationId(userId, lastNotificationId)
    }

    override suspend fun notificationExists(notificationId: NotificationId): Boolean =
        notificationsStorage.isNotificationExists(notificationId)

}
