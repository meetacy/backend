package app.meetacy.backend.feature.notifications.database.integration.notifications

import app.meetacy.backend.feature.notifications.database.NotificationsStorage
import app.meetacy.backend.feature.notifications.database.integration.types.ViewNotificationsRepository
import app.meetacy.backend.feature.notifications.database.integration.types.mapToUsecase
import app.meetacy.backend.feature.notifications.usecase.get.GetNotificationsViewsUsecase
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.notification.FullNotification
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun GetNotificationsViewsUsecase(
    db: Database,
    getMeetingsViewsRepository: GetMeetingsViewsRepository,
    getUsersViewsRepository: GetUsersViewsRepository
): GetNotificationsViewsUsecase {
    return GetNotificationsViewsUsecase(
        storage = DatabaseGetNotificationsViewsUsecaseStorage(db),
        viewRepository = ViewNotificationsRepository(db, getMeetingsViewsRepository, getUsersViewsRepository)
    )
}

class DatabaseGetNotificationsViewsUsecaseStorage(db: Database) : GetNotificationsViewsUsecase.Storage {
    private val notificationsStorage = NotificationsStorage(db)

    override suspend fun getNotifications(
        notificationIds: List<NotificationId>
    ): List<FullNotification?> = notificationsStorage
        .getNotificationsOrNull(notificationIds)
        .map { it?.mapToUsecase() }
}
