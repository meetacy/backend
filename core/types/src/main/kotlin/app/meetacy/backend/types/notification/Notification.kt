package app.meetacy.backend.types.notification

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserView

sealed interface FullNotification {
    val id: NotificationId
    val date: DateTime

    data class Subscription(
        override val id: NotificationId,
        override val date: DateTime,
        val subscriberId: UserId
    ) : FullNotification

    data class Invitation(
        override val id: NotificationId,
        override val date: DateTime,
        val meetingId: MeetingId,
        val inviterId: UserId
    ) : FullNotification
}

sealed interface NotificationView {
    val id: NotificationId
    val isNew: Boolean
    val date: DateTime

    data class Subscription(
        override val id: NotificationId,
        override val isNew: Boolean,
        override val date: DateTime,
        val subscriber: UserView
    ) : NotificationView

    data class Invitation(
        override val id: NotificationId,
        override val isNew: Boolean,
        override val date: DateTime,
        val meeting: MeetingView,
        val inviter: UserView,
    ) : NotificationView
}

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

fun DatabaseNotification.mapToUsecase(): FullNotification =
    when (this) {
        is DatabaseNotification.Subscription -> FullNotification.Subscription(id, date, subscriberId)
        is DatabaseNotification.Invitation -> FullNotification.Invitation(id, date, meetingId, inviterId)
    }
