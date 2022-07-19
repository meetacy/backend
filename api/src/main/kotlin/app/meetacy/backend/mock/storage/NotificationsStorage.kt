package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.UserId

object NotificationsStorage {
    private val data = mutableListOf<MockNotification>()

    fun addNotification(notification: MockNotification) {
        data += notification
    }

    fun getNotifications(userId: UserId, offset: Int, amount: Int) = data
        .filter { it.ownerId == userId }
        .drop(offset)
        .take(amount)
}
