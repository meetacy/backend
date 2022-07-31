package app.meetacy.backend.usecase.types

import app.meetacy.backend.domain.Date
import app.meetacy.backend.domain.NotificationId

sealed interface Notification {
    val id: NotificationId
    val isNew: Boolean

    class Subscription(
        override val id: NotificationId,
        override val isNew: Boolean,
        val subscriber: UserView,
        val date: Date
    ) : Notification

    class Invitation(
        override val id: NotificationId,
        override val isNew: Boolean,
        val meeting: MeetingView,
        val inviter: UserView,
        val date: Date
    ) : Notification
}
