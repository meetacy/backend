package app.meetacy.backend.database.integration.updates.stream

import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.FullUpdate
import app.meetacy.backend.usecase.types.GetNotificationsViewsRepository
import app.meetacy.backend.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.usecase.updates.stream.UpdatesMiddleware
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
