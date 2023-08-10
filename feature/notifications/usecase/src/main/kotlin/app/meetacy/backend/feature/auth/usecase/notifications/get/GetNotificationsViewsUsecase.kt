package app.meetacy.backend.feature.auth.usecase.notifications.get

import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.FullNotification
import app.meetacy.backend.usecase.types.NotificationView
import app.meetacy.backend.usecase.types.ViewNotificationsRepository

class GetNotificationsViewsUsecase(
    private val storage: Storage,
    private val viewRepository: ViewNotificationsRepository
) {
    suspend fun getNotificationsViewsOrNull(
        viewerId: UserId,
        notificationIds: List<NotificationId>
    ): List<NotificationView?> {
        val notifications = storage.getNotifications(notificationIds)

        val notificationViewsIterator = viewRepository.viewNotifications(
            viewerId = viewerId,
            notifications = notifications.filterNotNull()
        ).iterator()

        return notifications.map { fullNotification ->
            if (fullNotification == null) return@map null
            notificationViewsIterator.next()
        }
    }

    interface Storage {
        suspend fun getNotifications(notificationIds: List<NotificationId>): List<FullNotification?>
    }
}
