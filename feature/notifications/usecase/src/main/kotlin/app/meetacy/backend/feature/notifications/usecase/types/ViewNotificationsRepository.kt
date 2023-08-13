package app.meetacy.backend.feature.notifications.usecase.types

import app.meetacy.backend.types.users.UserId

interface ViewNotificationsRepository {
    suspend fun viewNotifications(viewerId: UserId, notifications: List<FullNotification>): List<NotificationView>
}
