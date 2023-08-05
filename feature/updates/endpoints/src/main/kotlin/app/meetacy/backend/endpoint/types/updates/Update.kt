package app.meetacy.backend.endpoint.types.updates

import app.meetacy.backend.types.serializable.update.UpdateId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import app.meetacy.backend.endpoint.types.notification.Notification as NotificationType

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
