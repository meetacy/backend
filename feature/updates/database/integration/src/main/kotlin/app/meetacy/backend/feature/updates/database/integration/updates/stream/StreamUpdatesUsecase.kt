package app.meetacy.backend.feature.updates.database.integration.updates.stream

import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.feature.updates.usecase.types.FullUpdate
import app.meetacy.backend.feature.notifications.usecase.types.GetNotificationsViewsRepository
import app.meetacy.backend.feature.updates.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.feature.updates.usecase.updates.stream.UpdatesMiddleware
import kotlinx.coroutines.flow.Flow

fun StreamUpdatesUsecase(
    auth: AuthRepository,
    notificationsRepository: GetNotificationsViewsRepository,
    updatesMiddleware: UpdatesMiddleware
): StreamUpdatesUsecase = StreamUpdatesUsecase(
    auth = auth,
    storage = DatabaseStreamUpdatesUsecaseStorage(updatesMiddleware),
    notificationsRepository = notificationsRepository
)

class DatabaseStreamUpdatesUsecaseStorage(
    private val middleware: UpdatesMiddleware
) : StreamUpdatesUsecase.Storage {
    override suspend fun updatesFlow(userId: UserId, fromId: UpdateId?): Flow<FullUpdate> {
        return middleware.updatesFlow(userId, fromId)
    }
}
