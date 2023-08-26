package app.meetacy.backend.feature.notifications.endpoints.integration.read

import app.meetacy.backend.feature.notifications.endpoints.read.ReadNotificationsRepository
import app.meetacy.backend.feature.notifications.endpoints.read.read
import app.meetacy.backend.feature.notifications.usecase.read.ReadNotificationsUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.notification.NotificationId
import app.meetacy.backend.types.serializable.notification.type
import app.meetacy.di.global.di
import io.ktor.server.routing.*

fun Route.read() {
    val usecase: ReadNotificationsUsecase by di.getting
    val repository = object : ReadNotificationsRepository {
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
    read(repository)
}
