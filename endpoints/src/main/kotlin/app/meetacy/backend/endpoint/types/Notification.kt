@file:UseSerializers(NotificationIdSerializer::class, DateSerializer::class)

package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.Date
import app.meetacy.backend.types.NotificationId
import app.meetacy.backend.types.serialization.DateSerializer
import app.meetacy.backend.types.serialization.NotificationIdSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
sealed interface Notification {
    val id: NotificationId
    val isNew: Boolean

    @SerialName("subscription")
    @Serializable
    class Subscription(
        override val isNew: Boolean,
        override val id: NotificationId,
        val subscriber: User,
        val date: Date
    ) : Notification

    @SerialName("meeting_invitation")
    @Serializable
    class Invitation(
        override val id: NotificationId,
        override val isNew: Boolean,
        val meeting: Meeting,
        val inviter: User,
        val date: Date
    ) : Notification
}
