package app.meetacy.backend.database.integration.notifications

import app.meetacy.backend.types.NotificationId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase

object MockGetNotificationStorage : GetNotificationsUsecase.Storage {
    override suspend fun getLastReadNotification(userId: UserId): NotificationId =
        LastReadNotification.getLastReadNotificationId(userId)

    override suspend fun getNotifications(
        userId: UserId,
        offset: Long,
        count: Int
    ): List<GetNotificationsUsecase.NotificationFromStorage> = NotificationsStorage
        .getNotifications(userId, offset.toInt(), count)
        .map(MockNotification::mapToUsecase)
}
