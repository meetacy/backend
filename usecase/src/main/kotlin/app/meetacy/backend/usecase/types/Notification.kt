package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId

sealed interface FullNotification {
    val id: NotificationId
    val isNew: Boolean

    class Subscription(
        override val id: NotificationId,
        override val isNew: Boolean,
        val subscriberId: UserId,
        val date: Date
    ) : FullNotification

    class Invitation(
        override val id: NotificationId,
        override val isNew: Boolean,
        val meetingId: MeetingId,
        val inviterId: UserId,
        val date: Date
    ) : FullNotification
}

sealed interface NotificationView {
    val id: NotificationId
    val isNew: Boolean

    class Subscription(
        override val id: NotificationId,
        override val isNew: Boolean,
        val subscriber: UserView,
        val date: Date
    ) : NotificationView

    class Invitation(
        override val id: NotificationId,
        override val isNew: Boolean,
        val meeting: MeetingView,
        val inviter: UserView,
        val date: Date
    ) : NotificationView
}
