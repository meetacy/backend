package app.meetacy.backend.database.integration.updates.stream

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.types.DatabaseUpdate
import app.meetacy.backend.database.updates.UpdatesStorage
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.FullUpdate
import app.meetacy.backend.usecase.updates.stream.UpdatesMiddleware
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.exposed.sql.Database

fun UpdatesMiddleware(db: Database): UpdatesMiddleware {
    return UpdatesMiddleware(storage = DatabaseUpdatesMiddlewareStorage(db))
}

class DatabaseUpdatesMiddlewareStorage(db: Database) : UpdatesMiddleware.Storage {
    private val updatesStorage = UpdatesStorage(db)

    override suspend fun addNotificationUpdate(
        userId: UserId,
        notificationId: NotificationId
    ): UpdateId = updatesStorage.addUpdate(userId, DatabaseUpdate.Type.Notification, notificationId.long)

    override fun pastUpdatesFlow(userId: UserId, fromId: UpdateId): Flow<FullUpdate> {
        return updatesStorage.getPastUpdatesFlow(userId, fromId).map { it.mapToUsecase() }
    }
}
