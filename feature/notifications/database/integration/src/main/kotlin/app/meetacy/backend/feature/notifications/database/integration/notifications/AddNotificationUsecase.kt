package app.meetacy.backend.feature.notifications.database.integration.notifications

import app.meetacy.backend.feature.notifications.database.notifications.NotificationsStorage
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.notifications.usecase.notifications.add.AddNotificationUsecase
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
