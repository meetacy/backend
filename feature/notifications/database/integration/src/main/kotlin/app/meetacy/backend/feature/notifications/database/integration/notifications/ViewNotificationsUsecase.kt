package app.meetacy.backend.feature.notifications.database.integration.notifications

import app.meetacy.backend.feature.notifications.database.LastReadNotificationsStorage
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.notifications.usecase.get.ViewNotificationsUsecase
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
