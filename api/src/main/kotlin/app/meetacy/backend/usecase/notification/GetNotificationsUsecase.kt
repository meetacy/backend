package app.meetacy.backend.usecase.notification

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.Notification
import app.meetacy.backend.usecase.types.authorize

class GetNotificationsUsecase(
    private val authRepository: AuthRepository,
    private val storage: Storage
) {
    suspend fun getNotifications(
        accessToken: AccessToken,
        offset: Long,
        count: Int
    ): Result {
        val userId = authRepository.authorize(accessToken) { return Result.TokenInvalid }
        val result = storage.getNotifications(userId, offset, count)
        return Result.Success(result)
    }

    sealed interface Result {
        object TokenInvalid : Result
        class Success(val result: List<Notification>) : Result
    }

    interface Storage {
        suspend fun getNotifications(userId: UserId, offset: Long, count: Int): List<Notification>
    }
}
