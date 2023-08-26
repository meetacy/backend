package app.meetacy.backend.types.update

import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.notification.NotificationView as NotificationType


sealed interface FullUpdate {
    val id: UpdateId

    data class Notification(
        override val id: UpdateId,
        val notificationId: NotificationId
    ) : FullUpdate
}

data class DatabaseUpdate(
    val id: UpdateId,
    val type: Type,
    val underlyingId: Long
) {
    enum class Type {
        Notification
    }
}

sealed interface UpdateView {
    val id: UpdateId

    data class Notification(
        override val id: UpdateId,
        val notification: NotificationType
    ) : UpdateView
}
