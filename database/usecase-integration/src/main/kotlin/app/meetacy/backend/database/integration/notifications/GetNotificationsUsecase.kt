package app.meetacy.backend.database.integration.notifications

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.notifications.LastReadNotificationsStorage
import app.meetacy.backend.database.notifications.NotificationsStorage
import app.meetacy.backend.database.types.DatabaseNotification
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetNotificationStorage(db: Database) : GetNotificationsUsecase.Storage {
    private val notificationsStorage = NotificationsStorage(db)
    private val lastReadNotificationsStorage = LastReadNotificationsStorage(db)

    override suspend fun getLastReadNotification(userId: UserId): NotificationId =
        lastReadNotificationsStorage.getLastReadNotificationId(userId)

    override suspend fun getNotifications(
        userId: UserId,
        offset: Long,
        count: Int
    ): List<GetNotificationsUsecase.NotificationFromStorage> =
        notificationsStorage
            .getNotifications(userId, offset, count)
            .map(DatabaseNotification::mapToUsecase)

}
