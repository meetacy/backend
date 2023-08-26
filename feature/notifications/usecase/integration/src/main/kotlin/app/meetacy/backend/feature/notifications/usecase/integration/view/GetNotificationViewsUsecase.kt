package app.meetacy.backend.feature.notifications.usecase.integration.view

import app.meetacy.backend.feature.notifications.database.NotificationsStorage
import app.meetacy.backend.feature.notifications.usecase.get.GetNotificationsViewsUsecase
import app.meetacy.backend.types.notification.FullNotification
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.notification.ViewNotificationsRepository
import app.meetacy.backend.types.notification.mapToUsecase
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.getNotificationViews() {
    val getNotificationsViewsUsecase by singleton<GetNotificationsViewsUsecase> {
        val repository: ViewNotificationsRepository by getting
        val storage = object : GetNotificationsViewsUsecase.Storage {
            private val notificationsStorage: NotificationsStorage by getting

            override suspend fun getNotifications(
                notificationIds: List<NotificationId>
            ): List<FullNotification?> = notificationsStorage
                .getNotificationsOrNull(notificationIds)
                .map { it?.mapToUsecase() }
        }

        GetNotificationsViewsUsecase(storage, repository)
    }
}
