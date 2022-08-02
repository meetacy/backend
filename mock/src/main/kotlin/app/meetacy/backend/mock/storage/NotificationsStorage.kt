package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.NotificationId
import app.meetacy.backend.types.UserId

object NotificationsStorage {
    private val data = mutableListOf<MockNotification>()

    fun addNotification(notification: MockNotification) {
        data += notification
    }

    fun getNotifications(userId: UserId, offset: Int, amount: Int) = data
        .filter { it.ownerId == userId }
        .drop(offset)
        .take(amount)

    fun isNotificationExists(notificationId: NotificationId): Boolean =
        data.any { it.id == notificationId }
}
