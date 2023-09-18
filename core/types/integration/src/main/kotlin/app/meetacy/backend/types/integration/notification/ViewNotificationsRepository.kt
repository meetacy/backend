package app.meetacy.backend.types.integration.notification

import app.meetacy.backend.feature.notifications.usecase.get.ViewNotificationsUsecase
import app.meetacy.backend.types.notification.FullNotification
import app.meetacy.backend.types.notification.NotificationView
import app.meetacy.backend.types.notification.ViewNotificationsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.viewNotifications() {
    val viewNotificationRepository by singleton<ViewNotificationsRepository> {
        val usecase: ViewNotificationsUsecase by getting
        object : ViewNotificationsRepository {
            override suspend fun viewNotifications(
                viewerId: UserId,
                notifications: List<FullNotification>
            ): List<NotificationView> = usecase.viewNotifications(viewerId, notifications)
        }
    }
}
