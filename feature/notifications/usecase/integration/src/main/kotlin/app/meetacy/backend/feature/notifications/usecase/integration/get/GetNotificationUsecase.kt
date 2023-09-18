package app.meetacy.backend.feature.notifications.usecase.integration.get

import app.meetacy.backend.feature.notifications.database.LastReadNotificationsStorage
import app.meetacy.backend.feature.notifications.database.NotificationsStorage
import app.meetacy.backend.feature.notifications.usecase.get.GetNotificationsUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.notification.*
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.getNotification() {
    val getNotificationUsecase by singleton<GetNotificationsUsecase> {
        val authRepository: AuthRepository by getting
        val viewNotificationsRepository: ViewNotificationsRepository by getting
        val storage = object : GetNotificationsUsecase.Storage {
            private val notificationsStorage: NotificationsStorage by getting
            private val lastReadNotificationsStorage: LastReadNotificationsStorage by getting

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

        GetNotificationsUsecase(authRepository, viewNotificationsRepository, storage)
    }
}
