package app.meetacy.backend.feature.notifications.database.integration.notifications

import app.meetacy.backend.feature.notifications.database.integration.types.ViewNotificationsRepository
import app.meetacy.backend.feature.notifications.database.integration.types.mapToUsecase
import app.meetacy.backend.feature.notifications.database.LastReadNotificationsStorage
import app.meetacy.backend.feature.notifications.database.NotificationsStorage
import app.meetacy.backend.feature.notifications.database.types.DatabaseNotification
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.notifications.usecase.get.GetNotificationsUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.feature.notifications.usecase.types.FullNotification
import org.jetbrains.exposed.sql.Database

fun GetNotificationsUsecase(
    db: Database,
    authRepository: AuthRepository,
    meetingsRepository: GetMeetingsViewsRepository,
    usersRepository: GetUsersViewsRepository
): GetNotificationsUsecase {
    return GetNotificationsUsecase(
        authRepository = authRepository,
        viewNotificationsRepository = ViewNotificationsRepository(db, meetingsRepository, usersRepository),
        storage = DatabaseGetNotificationStorage(db)
    )
}

class DatabaseGetNotificationStorage(db: Database) : GetNotificationsUsecase.Storage {
    private val notificationsStorage = NotificationsStorage(db)
    private val lastReadNotificationsStorage = LastReadNotificationsStorage(db)

    override suspend fun getLastReadNotification(userId: UserId): NotificationId =
        lastReadNotificationsStorage.getLastReadNotificationId(userId)

    override suspend fun getNotifications(
        userId: UserId,
        pagingId: PagingId?,
        amount: Amount,
    ): PagingResult<FullNotification> =
        notificationsStorage
            .getNotifications(userId, pagingId, amount)
            .mapItems(DatabaseNotification::mapToUsecase)

}
