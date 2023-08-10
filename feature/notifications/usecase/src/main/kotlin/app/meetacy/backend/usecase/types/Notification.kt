package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.UserId

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
