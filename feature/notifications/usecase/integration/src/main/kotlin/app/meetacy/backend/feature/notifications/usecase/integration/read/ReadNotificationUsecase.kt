package app.meetacy.backend.feature.notifications.usecase.integration.read

import app.meetacy.backend.feature.notifications.database.LastReadNotificationsStorage
import app.meetacy.backend.feature.notifications.database.NotificationsStorage
import app.meetacy.backend.feature.notifications.usecase.read.ReadNotificationsUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.readNotification() {
    val readNotificationUsecase by singleton<ReadNotificationsUsecase> {
        val authRepository: AuthRepository by getting
        val storage = object : ReadNotificationsUsecase.Storage {
            private val notificationsStorage: NotificationsStorage by getting
            private val lastReadNotificationsStorage: LastReadNotificationsStorage by getting

            override suspend fun markAsRead(userId: UserId, lastNotificationId: NotificationId) {
                lastReadNotificationsStorage.setLastReadNotificationId(userId, lastNotificationId)
            }

            override suspend fun notificationExists(notificationId: NotificationId): Boolean =
                notificationsStorage.isNotificationExists(notificationId)

        }

        ReadNotificationsUsecase(authRepository, storage)
    }
}
