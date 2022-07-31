package app.meetacy.backend.usecase.integration.notificationsRead

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.domain.NotificationId
import app.meetacy.backend.endpoint.notifications.read.ReadNotificationsRepository
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase

private class Integration(
    private val usecase: ReadNotificationsUsecase
) : ReadNotificationsRepository {
    override suspend fun read(
        accessToken: AccessToken,
        lastNotificationId: NotificationId
    ): ReadNotificationsRepository.Result =
        when (usecase.read(accessToken, lastNotificationId)) {
            ReadNotificationsUsecase.Result.LastNotificationIdInvalid ->
                ReadNotificationsRepository.Result.LastNotificationIdInvalid
            ReadNotificationsUsecase.Result.Success ->
                ReadNotificationsRepository.Result.Success
            ReadNotificationsUsecase.Result.TokenInvalid ->
                ReadNotificationsRepository.Result.TokenInvalid
        }
}

fun usecaseReadNotificationsRepository(usecase: ReadNotificationsUsecase): ReadNotificationsRepository =
    Integration(usecase)
