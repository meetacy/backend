package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId

sealed interface FullNotification {
    val id: NotificationId
    val date: Date

    class Subscription(
        override val id: NotificationId,
        override val date: Date,
        val subscriberId: UserId
    ) : FullNotification

    class Invitation(
        override val id: NotificationId,
        override val date: Date,
        val meetingId: MeetingId,
        val inviterId: UserId
    ) : FullNotification
}

sealed interface NotificationView {
    val id: NotificationId
    val isNew: Boolean
    val date: Date

    class Subscription(
        override val id: NotificationId,
        override val isNew: Boolean,
        override val date: Date,
        val subscriber: UserView
    ) : NotificationView

    class Invitation(
        override val id: NotificationId,
        override val isNew: Boolean,
        override val date: Date,
        val meeting: MeetingView,
        val inviter: UserView,
    ) : NotificationView
}
