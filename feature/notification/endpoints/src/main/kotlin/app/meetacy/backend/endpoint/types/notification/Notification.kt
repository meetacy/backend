package app.meetacy.backend.endpoint.types.notification

import app.meetacy.backend.endpoint.types.meeting.Meeting
import app.meetacy.backend.endpoint.types.user.User
import app.meetacy.backend.types.serialization.datetime.DateTimeSerializable
import app.meetacy.backend.types.serialization.notification.NotificationIdSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Notification {
    abstract val id: NotificationIdSerializable
    abstract val isNew: Boolean
    abstract val date: DateTimeSerializable

    @SerialName("subscription")
    @Serializable
    class Subscription(
        override val isNew: Boolean,
        override val id: NotificationIdSerializable,
        override val date: DateTimeSerializable,
        val subscriber: User,
    ) : Notification()

    @SerialName("meeting_invitation")
    @Serializable
    class Invitation(
        override val id: NotificationIdSerializable,
        override val isNew: Boolean,
        override val date: DateTimeSerializable,
        val meeting: Meeting,
        val inviter: User,
    ) : Notification()
}
