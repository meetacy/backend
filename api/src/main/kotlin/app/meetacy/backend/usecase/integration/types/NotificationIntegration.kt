package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.Notification as EndpointNotification
import app.meetacy.backend.usecase.types.Notification as UsecaseNotification

fun UsecaseNotification.mapToEndpoint(): EndpointNotification =
    when (this) {
        is UsecaseNotification.Invitation -> EndpointNotification.Invitation(
            id = id,
            isNew = isNew,
            meeting = meeting.mapToEndpoint(),
            date = date,
            inviter = inviter.mapToDomain()
        )
        is UsecaseNotification.Subscription -> EndpointNotification.Subscription(
            id = id,
            isNew = isNew,
            subscriber = subscriber.mapToDomain(),
            date = date
        )
    }
