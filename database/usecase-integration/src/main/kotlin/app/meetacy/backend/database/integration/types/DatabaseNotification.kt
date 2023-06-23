package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.types.DatabaseNotification
import app.meetacy.backend.usecase.types.FullNotification

fun DatabaseNotification.mapToUsecase(): FullNotification =
    when (type) {
        DatabaseNotification.Type.Subscription ->
            FullNotification.Subscription(
                id, date, subscriberId!!
            )
        DatabaseNotification.Type.Invitation ->
            FullNotification.Invitation(
                id, date, invitedMeetingId!!, inviterId!!
            )
    }
