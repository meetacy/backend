package app.meetacy.backend.usecase.integration.notifications.read

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.NotificationId
import app.meetacy.backend.endpoint.notifications.read.ReadNotificationsRepository
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase

class UsecaseReadNotificationsRepository(
    private val usecase: ReadNotificationsUsecase
) : ReadNotificationsRepository {
    override suspend fun read(
        accessIdentity: AccessIdentity,
        lastNotificationId: NotificationId
    ): ReadNotificationsRepository.Result =
        when (usecase.read(accessIdentity, lastNotificationId)) {
            ReadNotificationsUsecase.Result.LastNotificationIdInvalid ->
                ReadNotificationsRepository.Result.LastNotificationIdInvalid
            ReadNotificationsUsecase.Result.Success ->
                ReadNotificationsRepository.Result.Success
            ReadNotificationsUsecase.Result.TokenInvalid ->
                ReadNotificationsRepository.Result.TokenInvalid
        }
}
