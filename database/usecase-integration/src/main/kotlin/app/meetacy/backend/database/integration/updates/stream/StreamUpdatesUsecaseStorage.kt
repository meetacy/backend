package app.meetacy.backend.database.integration.updates.stream

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.notifications.NotificationsStorage
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.FullUpdate
import app.meetacy.backend.usecase.types.NotificationView
import app.meetacy.backend.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.usecase.updates.stream.UpdatesMiddleware
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.Database

class DatabaseStreamUpdatesUsecaseStorage(
    db: Database,
    private val middleware: UpdatesMiddleware
) : StreamUpdatesUsecase.Storage {
    private val notificationsStorage = NotificationsStorage(db)

    override suspend fun updatesFlow(userId: UserId, fromId: UpdateId?): Flow<FullUpdate> {
        return middleware.updatesFlow(userId, fromId)
    }

    override suspend fun getNotification(notificationId: NotificationId): NotificationView {
        return notificationsStorage.getNotification(notificationId).mapToUsecase()
    }
}
