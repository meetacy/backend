package app.meetacy.backend.endpoint.types.updates

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import app.meetacy.backend.endpoint.types.notification.Notification as NotificationType

@Serializable
sealed interface Update {
    @Serializable
    @SerialName("notification")
    data class Notification(
        val notification: NotificationType
    ) : Update
}
