package app.meetacy.backend.usecase.notifications

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.*

class GetNotificationsUsecase(
    private val authRepository: AuthRepository,
    private val viewNotificationsRepository: ViewNotificationsRepository,
    private val storage: Storage
) {
    suspend fun getNotifications(
        accessIdentity: AccessIdentity,
        pagingId: PagingId?,
        amount: Amount
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }
        val fullNotifications = storage.getNotifications(userId, pagingId, amount)
        val notificationsViews = viewNotificationsRepository.viewNotifications(userId, fullNotifications.data)
        val result = fullNotifications.map { notificationsViews }
        return Result.Success(result)
    }

    sealed interface Result {
        object TokenInvalid : Result
        class Success(val notifications: PagingResult<NotificationView>) : Result
    }

    interface Storage {
        suspend fun getLastReadNotification(userId: UserId): NotificationId
        suspend fun getNotifications(userId: UserId, pagingId: PagingId?, amount: Amount): PagingResult<FullNotification>
    }
}
