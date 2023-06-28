package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.types.DatabaseNotification
import app.meetacy.backend.usecase.types.FullNotification

fun DatabaseNotification.mapToUsecase(): FullNotification =
    when (this) {
        is DatabaseNotification.Subscription -> FullNotification.Subscription(id, date, subscriberId)
        is DatabaseNotification.Invitation -> FullNotification.Invitation(id, date, meetingId, inviterId)
    }
