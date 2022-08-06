package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.DateSerializable
import app.meetacy.backend.types.serialization.NotificationIdSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Notification {
    val id: NotificationIdSerializable
    val isNew: Boolean

    @SerialName("subscription")
    @Serializable
    class Subscription(
        override val isNew: Boolean,
        override val id: NotificationIdSerializable,
        val subscriber: User,
        val date: DateSerializable
    ) : Notification

    @SerialName("meeting_invitation")
    @Serializable
    class Invitation(
        override val id: NotificationIdSerializable,
        override val isNew: Boolean,
        val meeting: Meeting,
        val inviter: User,
        val date: DateSerializable
    ) : Notification
}
