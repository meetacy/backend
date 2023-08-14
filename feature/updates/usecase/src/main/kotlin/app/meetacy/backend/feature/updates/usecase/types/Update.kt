package app.meetacy.backend.feature.updates.usecase.types

import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.feature.notifications.usecase.types.NotificationView as NotificationType

sealed interface FullUpdate {
    val id: UpdateId

    data class Notification(
        override val id: UpdateId,
        val notificationId: NotificationId
    ) : FullUpdate
}

sealed interface UpdateView {
    val id: UpdateId

    data class Notification(
        override val id: UpdateId,
        val notification: NotificationType
    ) : UpdateView
}
