package app.meetacy.backend.mock.integration.types

import app.meetacy.backend.mock.storage.MockNotification
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase

fun MockNotification.mapToUsecase(): GetNotificationsUsecase.NotificationFromStorage =
    when (type) {
        MockNotification.Type.Subscription ->
            GetNotificationsUsecase.NotificationFromStorage.Subscription(
                id, subscriberId!!, date
            )
        MockNotification.Type.Invitation ->
            GetNotificationsUsecase.NotificationFromStorage.Invitation(
                id, invitedMeetingId!!, inviterId!!, date
            )
    }
