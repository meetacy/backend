package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.NotificationId
import app.meetacy.backend.domain.UserId

object LastReadNotificationStorage {
    private val data = mutableMapOf<UserId, NotificationId>()

    fun setLastReadNotificationId(userId: UserId, notificationId: NotificationId) {
        data[userId] = notificationId
    }

    fun getLastReadNotificationId(userId: UserId): NotificationId =
        data[userId] ?: NotificationId(long = 0)
}
