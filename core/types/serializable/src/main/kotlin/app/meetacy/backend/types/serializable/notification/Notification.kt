package app.meetacy.backend.types.serializable.notification

import app.meetacy.backend.types.serializable.meetings.Meeting
import app.meetacy.backend.types.serializable.users.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.datetime.DateTime as DateTimeSerializable

@Serializable
sealed class Notification {
    abstract val id: NotificationId
    abstract val isNew: Boolean
    abstract val date: DateTimeSerializable

    @SerialName("subscription")
    @Serializable
    class Subscription(
        override val isNew: Boolean,
        override val id: NotificationId,
        override val date: DateTimeSerializable,
        val subscriber: User,
    ) : Notification()

    @SerialName("meeting_invitation")
    @Serializable
    class Invitation(
        override val id: NotificationId,
        override val isNew: Boolean,
        override val date: DateTimeSerializable,
        val meeting: Meeting,
        val inviter: User,
    ) : Notification()
}
