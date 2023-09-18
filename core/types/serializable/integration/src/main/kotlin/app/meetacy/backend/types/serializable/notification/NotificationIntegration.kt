package app.meetacy.backend.types.serializable.notification

import app.meetacy.backend.types.serializable.datetime.serializable
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.types.serializable.users.mapToEndpoint
import app.meetacy.backend.types.notification.NotificationView as UsecaseNotification
import app.meetacy.backend.types.serializable.notification.Notification as EndpointNotification

fun UsecaseNotification.mapToEndpoint(): EndpointNotification =
    when (this) {
        is UsecaseNotification.Invitation -> EndpointNotification.Invitation(
            id = id.serializable(),
            isNew = isNew,
            meeting = meeting.serializable(),
            date = date.serializable(),
            inviter = inviter.mapToEndpoint()
        )
        is UsecaseNotification.Subscription -> EndpointNotification.Subscription(
            id = id.serializable(),
            isNew = isNew,
            subscriber = subscriber.mapToEndpoint(),
            date = date.serializable()
        )
    }
