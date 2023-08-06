package app.meetacy.backend.database.integration.notifications

import app.meetacy.backend.database.notifications.LastReadNotificationsStorage
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.notifications.get.ViewNotificationsUsecase
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun ViewNotificationsUsecase(
    db: Database,
    meetingsRepository: GetMeetingsViewsRepository,
    usersRepository: GetUsersViewsRepository
) = ViewNotificationsUsecase(
    storage = DatabaseViewNotificationsUsecaseStorage(db),
    meetingsRepository, usersRepository
)

class DatabaseViewNotificationsUsecaseStorage(db: Database) : ViewNotificationsUsecase.Storage {
    private val lastReadNotificationsStorage = LastReadNotificationsStorage(db)

    override suspend fun getLastReadNotificationId(userId: UserId): NotificationId {
        return lastReadNotificationsStorage.getLastReadNotificationId(userId)
    }
}
