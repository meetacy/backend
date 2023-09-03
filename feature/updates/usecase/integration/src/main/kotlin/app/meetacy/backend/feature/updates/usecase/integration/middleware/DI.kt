package app.meetacy.backend.feature.updates.usecase.integration.middleware

import app.meetacy.backend.feature.updates.database.updates.DatabaseUpdateType
import app.meetacy.backend.feature.updates.database.updates.UpdatesStorage
import app.meetacy.backend.feature.updates.usecase.updates.stream.UpdatesMiddleware
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.update.FullUpdate
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder
import kotlinx.coroutines.flow.Flow

fun DIBuilder.updatesMiddleware() {
    val updatesMiddleware by singleton<UpdatesMiddleware> {
        val storage = object : UpdatesMiddleware.Storage {
            private val updatesStorage: UpdatesStorage by getting

            override suspend fun addNotificationUpdate(
                userId: UserId,
                notificationId: NotificationId
            ): UpdateId = updatesStorage.addUpdate(userId, DatabaseUpdateType.Notification, notificationId.long)

            override fun pastUpdatesFlow(userId: UserId, fromId: UpdateId): Flow<FullUpdate> {
                return updatesStorage.getPastUpdatesFlow(userId, fromId)
            }
        }
        UpdatesMiddleware(storage)
    }
}
