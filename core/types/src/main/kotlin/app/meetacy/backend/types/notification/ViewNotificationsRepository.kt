package app.meetacy.backend.types.notification

import app.meetacy.backend.types.users.UserId

interface ViewNotificationsRepository {
    suspend fun viewNotifications(viewerId: UserId, notifications: List<FullNotification>): List<NotificationView>
}
