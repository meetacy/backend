package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.types.serializable.datetime.serializable
import app.meetacy.backend.types.serialization.notification.serializable
import app.meetacy.backend.endpoint.types.notification.Notification as EndpointNotification
import app.meetacy.backend.usecase.types.NotificationView as UsecaseNotification

fun UsecaseNotification.mapToEndpoint(): EndpointNotification =
    when (this) {
        is UsecaseNotification.Invitation -> EndpointNotification.Invitation(
            id = id.serializable(),
            isNew = isNew,
            meeting = meeting.mapToEndpoint(),
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
