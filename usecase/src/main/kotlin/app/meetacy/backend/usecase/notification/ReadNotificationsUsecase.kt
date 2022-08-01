package app.meetacy.backend.usecase.notification

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.NotificationId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.authorize

class ReadNotificationsUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {

    suspend fun read(
        accessToken: AccessToken,
        lastNotificationId: NotificationId
    ): Result {
        val userId = authRepository.authorize(accessToken) { return Result.TokenInvalid }

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
