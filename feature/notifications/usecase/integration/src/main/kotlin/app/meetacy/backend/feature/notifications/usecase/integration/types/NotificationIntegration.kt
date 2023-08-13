package app.meetacy.backend.feature.notifications.usecase.integration.types

import app.meetacy.backend.types.serializable.datetime.serializable
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.types.serializable.notification.serializable
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.types.serializable.notification.Notification as EndpointNotification
import app.meetacy.backend.feature.notifications.usecase.types.NotificationView as UsecaseNotification

fun UsecaseNotification.mapToEndpoint(): EndpointNotification =
    when (this) {
        is UsecaseNotification.Invitation -> EndpointNotification.Invitation(
            id = id.serializable(),
            isNew = isNew,
            meeting = meeting.type(),
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
