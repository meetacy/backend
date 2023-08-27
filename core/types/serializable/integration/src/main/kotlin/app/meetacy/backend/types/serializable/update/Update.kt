package app.meetacy.backend.types.serializable.update

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.notification.Notification as NotificationType

@Serializable
sealed interface Update {
    val id: UpdateId

    @Serializable
    @SerialName("notification")
    data class Notification(
        override val id: UpdateId,
        val notification: NotificationType
    ) : Update
}
