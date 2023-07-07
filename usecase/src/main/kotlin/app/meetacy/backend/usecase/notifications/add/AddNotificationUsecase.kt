package app.meetacy.backend.usecase.notifications.add

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId

class AddNotificationUsecase(private val storage: Storage) {
    suspend fun addSubscription(
        userId: UserId,
        subscriberId: UserId,
        date: DateTime
    ) {
        val notificationId = storage.addSubscription(userId, subscriberId, date)
        storage.addUpdate(userId, notificationId)
    }

    suspend fun addInvitation(
        userId: UserId,
        inviterId: UserId,
        meetingId: MeetingId,
        date: DateTime
    ) {
        val notificationId = storage.addInvitation(userId, inviterId, meetingId, date)
        storage.addUpdate(userId, notificationId)
    }

    interface Storage {
        suspend fun addSubscription(
            userId: UserId,
            subscriberId: UserId,
            date: DateTime
        ): NotificationId

        suspend fun addInvitation(
            userId: UserId,
            inviterId: UserId,
            meetingId: MeetingId,
            date: DateTime
        ): NotificationId

        suspend fun addUpdate(
            userId: UserId,
            notificationId: NotificationId
        )
    }
}
