package app.meetacy.backend.types.integration.notification

import app.meetacy.backend.feature.notifications.usecase.get.GetNotificationsViewsUsecase
import app.meetacy.backend.types.notification.GetNotificationsViewsRepository
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.notification.NotificationView
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.getNotificationViews() {
    val getNotificationViewsRepository by singleton<GetNotificationsViewsRepository> {
        val usecase: GetNotificationsViewsUsecase by getting
        object : GetNotificationsViewsRepository {
            override suspend fun getNotificationsViewsOrNull(
                viewerId: UserId,
                notificationIds: List<NotificationId>
            ): List<NotificationView?> {
                return usecase.getNotificationsViewsOrNull(viewerId, notificationIds)
            }
        }
    }
}
