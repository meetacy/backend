package app.meetacy.backend.database.integration.notifications

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.notifications.LastReadNotificationsTable
import app.meetacy.backend.database.notifications.NotificationsTable
import app.meetacy.backend.database.types.DatabaseNotification
import app.meetacy.backend.types.NotificationId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetNotificationStorage(db: Database) : GetNotificationsUsecase.Storage {
    private val notificationsTable = NotificationsTable(db)
    private val lastReadNotificationsTable = LastReadNotificationsTable(db)

    override suspend fun getLastReadNotification(userId: UserId): NotificationId =
        lastReadNotificationsTable.getLastReadNotificationId(userId)

    override suspend fun getNotifications(
        userId: UserId,
        offset: Long,
        count: Int
    ): List<GetNotificationsUsecase.NotificationFromStorage> =
        notificationsTable
            .getNotifications(userId, offset.toInt(), count)
            .map(DatabaseNotification::mapToUsecase)

}
