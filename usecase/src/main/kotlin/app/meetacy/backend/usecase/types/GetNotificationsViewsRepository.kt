package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId

interface GetNotificationsViewsRepository {
    suspend fun getNotificationsViewsOrNull(viewerId: UserId, notificationIds: List<NotificationId>): List<NotificationView?>
}
