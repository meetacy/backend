package app.meetacy.backend.database.integration.notifications

import app.meetacy.backend.database.notifications.NotificationsStorage
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.notifications.add.AddNotificationUsecase
import app.meetacy.backend.usecase.updates.stream.UpdatesMiddleware
import org.jetbrains.exposed.sql.Database

fun AddNotificationUsecase(db: Database, updatesMiddleware: UpdatesMiddleware): AddNotificationUsecase =
    AddNotificationUsecase(
        storage = DatabaseAddNotificationUsecaseStorage(db, updatesMiddleware)
    )

class DatabaseAddNotificationUsecaseStorage(
    db: Database,
    private val updatesMiddleware: UpdatesMiddleware
) : AddNotificationUsecase.Storage {
    private val notificationsStorage = NotificationsStorage(db)

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
