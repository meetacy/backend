package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.NotificationId
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.mock.storage.LastReadNotificationStorage
import app.meetacy.backend.mock.storage.NotificationsStorage
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase

private object ReadNotificationsUsecaseStorage : ReadNotificationsUsecase.Storage {
    override suspend fun markAsRead(userId: UserId, lastNotificationId: NotificationId) =
        LastReadNotificationStorage.setLastReadNotificationId(userId, lastNotificationId)

    override suspend fun notificationExists(notificationId: NotificationId): Boolean =
        NotificationsStorage.isNotificationExists(notificationId)
}

fun mockReadNotificationsUsecase() = ReadNotificationsUsecase(MockAuthRepository, ReadNotificationsUsecaseStorage)
