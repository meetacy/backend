package app.meetacy.backend.feature.notifications.usecase.integration.add

import app.meetacy.backend.feature.notifications.database.NotificationsStorage
import app.meetacy.backend.feature.notifications.usecase.add.AddNotificationUsecase
import app.meetacy.backend.feature.updates.usecase.updates.stream.UpdatesMiddleware
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.addNotification() {
    val addNotificationUsecase by singleton<AddNotificationUsecase> {
        val updatesMiddleware: UpdatesMiddleware by getting
        val storage = object : AddNotificationUsecase.Storage {
            private val notificationsStorage: NotificationsStorage by getting

            override suspend fun addSubscription(userId: UserId, subscriberId: UserId, date: DateTime): NotificationId {
                return notificationsStorage.addSubscriptionNotification(userId, subscriberId, date)
            }

            override suspend fun addInvitation(
                userId: UserId,
                inviterId: UserId,
                meetingId: MeetingId,
                date: DateTime
            ): NotificationId {
                return notificationsStorage.addInvitationNotification(userId, inviterId, meetingId, date)
            }

            override suspend fun addUpdate(userId: UserId, notificationId: NotificationId) {
                updatesMiddleware.addNotificationUpdate(userId, notificationId)
            }
        }

        AddNotificationUsecase(storage)
    }
}
