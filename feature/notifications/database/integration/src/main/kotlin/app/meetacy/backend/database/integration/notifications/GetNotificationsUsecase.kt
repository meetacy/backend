package app.meetacy.backend.database.integration.notifications

import app.meetacy.backend.feature.auth.database.integration.types.ViewNotificationsRepository
import app.meetacy.backend.feature.auth.database.integration.types.mapToUsecase
import app.meetacy.backend.database.notifications.LastReadNotificationsStorage
import app.meetacy.backend.database.notifications.NotificationsStorage
import app.meetacy.backend.database.types.DatabaseNotification
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.notifications.get.GetNotificationsUsecase
import app.meetacy.backend.feature.auth.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FullNotification
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import org.jetbrains.exposed.sql.Database

fun GetNotificationsUsecase(
    db: Database,
    authRepository: app.meetacy.backend.feature.auth.usecase.types.AuthRepository,
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
