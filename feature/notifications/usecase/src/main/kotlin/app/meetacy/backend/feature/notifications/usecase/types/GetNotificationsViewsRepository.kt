package app.meetacy.backend.feature.notifications.usecase.types

import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.UserId

interface GetNotificationsViewsRepository {
    suspend fun getNotificationsViewsOrNull(viewerId: UserId, notificationIds: List<NotificationId>): List<NotificationView?>
}

suspend fun GetNotificationsViewsRepository.getNotificationViewOrNull(
    viewerId: UserId,
    notificationId: NotificationId
): NotificationView? = getNotificationsViewsOrNull(viewerId, listOf(notificationId)).first()

suspend fun GetNotificationsViewsRepository.getNotificationView(
    viewerId: UserId,
    notificationId: NotificationId
): NotificationView = getNotificationViewOrNull(viewerId, notificationId) ?: error("Cannot find notification with id $notificationId")
