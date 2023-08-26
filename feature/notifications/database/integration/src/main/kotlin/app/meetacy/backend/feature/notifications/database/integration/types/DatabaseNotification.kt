package app.meetacy.backend.feature.notifications.database.integration.types

import app.meetacy.backend.types.notification.DatabaseNotification
import app.meetacy.backend.types.notification.FullNotification

fun DatabaseNotification.mapToUsecase(): FullNotification =
    when (this) {
        is DatabaseNotification.Subscription -> FullNotification.Subscription(id, date, subscriberId)
        is DatabaseNotification.Invitation -> FullNotification.Invitation(id, date, meetingId, inviterId)
    }
