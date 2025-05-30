package app.meetacy.backend.feature.notifications.usecase.get

import app.meetacy.backend.types.notification.FullNotification
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.notification.NotificationView
import app.meetacy.backend.types.notification.ViewNotificationsRepository
import app.meetacy.backend.types.users.UserId

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
