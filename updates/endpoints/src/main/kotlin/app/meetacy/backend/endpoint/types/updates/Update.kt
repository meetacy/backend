package app.meetacy.backend.endpoint.types.updates

import app.meetacy.backend.types.serialization.update.UpdateIdSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import app.meetacy.backend.endpoint.types.notification.Notification as NotificationType

@Serializable
sealed interface Update {
    val id: UpdateIdSerializable

    @Serializable
    @SerialName("notification")
    data class Notification(
        override val id: UpdateIdSerializable,
        val notification: NotificationType
    ) : Update
}
