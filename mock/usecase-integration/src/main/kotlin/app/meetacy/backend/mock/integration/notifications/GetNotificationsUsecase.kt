package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.integration.types.mapToUsecase
import app.meetacy.backend.types.NotificationId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.mock.storage.LastReadNotificationStorage
import app.meetacy.backend.mock.storage.MockNotification
import app.meetacy.backend.mock.storage.NotificationsStorage
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase.NotificationFromStorage

object MockGetNotificationStorage : GetNotificationsUsecase.Storage {
    override suspend fun getLastReadNotification(userId: UserId): NotificationId =
        LastReadNotificationStorage.getLastReadNotificationId(userId)

    override suspend fun getNotifications(
        userId: UserId,
        offset: Long,
        count: Int
    ): List<NotificationFromStorage> = NotificationsStorage
        .getNotifications(userId, offset.toInt(), count)
        .map(MockNotification::mapToUsecase)
}
