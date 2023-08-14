package app.meetacy.backend.feature.auth.usecase.integration.notifications.read

import app.meetacy.backend.endpoint.notifications.read.ReadNotificationsRepository
import app.meetacy.backend.feature.auth.usecase.notifications.ReadNotificationsUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.notification.NotificationId
import app.meetacy.backend.types.serializable.notification.type

class UsecaseReadNotificationsRepository(
    private val usecase: ReadNotificationsUsecase
) : ReadNotificationsRepository {
    override suspend fun read(
        accessIdentity: AccessIdentity,
        lastNotificationId: NotificationId
    ): ReadNotificationsRepository.Result =
        when (usecase.read(accessIdentity.type(), lastNotificationId.type())) {
            ReadNotificationsUsecase.Result.LastNotificationIdInvalid ->
                ReadNotificationsRepository.Result.LastNotificationIdInvalid
            ReadNotificationsUsecase.Result.Success ->
                ReadNotificationsRepository.Result.Success
            ReadNotificationsUsecase.Result.TokenInvalid ->
                ReadNotificationsRepository.Result.InvalidIdentity
        }
}
