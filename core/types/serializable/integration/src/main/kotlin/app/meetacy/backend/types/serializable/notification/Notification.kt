package app.meetacy.backend.types.serializable.notification

import app.meetacy.backend.types.notification.NotificationView
import app.meetacy.backend.types.serializable.datetime.serializable
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.types.serializable.users.serializable

fun NotificationView.serializable(): Notification =
    when (this) {
        is NotificationView.Invitation -> Notification.Invitation(
            id = id.serializable(),
            isNew = isNew,
            meeting = meeting.serializable(),
            date = date.serializable(),
            inviter = inviter.serializable()
        )
        is NotificationView.Subscription -> Notification.Subscription(
            id = id.serializable(),
            isNew = isNew,
            subscriber = subscriber.serializable(),
            date = date.serializable()
        )
    }
