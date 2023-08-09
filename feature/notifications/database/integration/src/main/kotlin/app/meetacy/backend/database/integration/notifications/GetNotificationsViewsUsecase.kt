package app.meetacy.backend.database.integration.notifications

import app.meetacy.backend.feature.auth.database.integration.types.ViewNotificationsRepository
import app.meetacy.backend.feature.auth.database.integration.types.mapToUsecase
import app.meetacy.backend.database.notifications.NotificationsStorage
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.usecase.notifications.get.GetNotificationsViewsUsecase
import app.meetacy.backend.usecase.types.FullNotification
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
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
