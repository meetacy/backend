package app.meetacy.backend.database.types

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId

sealed interface DatabaseNotification {
    val id: NotificationId
    val ownerId: UserId
    val date: DateTime

    data class Subscription(
        override val id: NotificationId,
        override val ownerId: UserId,
        override val date: DateTime,
        val subscriberId: UserId
    ) : DatabaseNotification

    data class Invitation(
        override val id: NotificationId,
        override val ownerId: UserId,
        override val date: DateTime,
        val inviterId: UserId,
        val meetingId: MeetingId
    ) : DatabaseNotification
}
