package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.types.DatabaseNotification
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase

fun DatabaseNotification.mapToUsecase(): GetNotificationsUsecase.NotificationFromStorage =
    when (type) {
        DatabaseNotification.Type.Subscription ->
            GetNotificationsUsecase.NotificationFromStorage.Subscription(
                id, subscriberId!!, date
            )
        DatabaseNotification.Type.Invitation ->
            GetNotificationsUsecase.NotificationFromStorage.Invitation(
                id, invitedMeetingId!!, inviterId!!, date
            )
    }
