package app.meetacy.backend.feature.notifications.usecase.read

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId

class ReadNotificationsUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {

    suspend fun read(
        accessIdentity: AccessIdentity,
        lastNotificationId: NotificationId
    ): Result {
        val userId = authRepository.authorizeWithUserId(accessIdentity) { return Result.TokenInvalid }

        if (!storage.notificationExists(lastNotificationId))
            return Result.LastNotificationIdInvalid

        storage.markAsRead(userId, lastNotificationId)

        return Result.Success
    }

    sealed interface Result {
        object TokenInvalid : Result
        object LastNotificationIdInvalid : Result
        object Success : Result
    }

    interface Storage {
        suspend fun markAsRead(userId: UserId, lastNotificationId: NotificationId)
        suspend fun notificationExists(notificationId: NotificationId): Boolean
    }
}
