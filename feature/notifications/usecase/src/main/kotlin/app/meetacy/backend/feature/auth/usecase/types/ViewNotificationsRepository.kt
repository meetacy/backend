package app.meetacy.backend.feature.auth.usecase.types

import app.meetacy.backend.types.user.UserId

interface ViewNotificationsRepository {
    suspend fun viewNotifications(viewerId: UserId, notifications: List<FullNotification>): List<NotificationView>
}
