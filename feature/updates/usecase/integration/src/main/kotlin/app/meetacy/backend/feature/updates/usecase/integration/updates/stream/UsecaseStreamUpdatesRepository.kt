package app.meetacy.backend.feature.updates.usecase.integration.updates.stream

import app.meetacy.backend.feature.updates.endpoints.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.feature.updates.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.feature.updates.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.feature.updates.usecase.updates.stream.StreamUpdatesUsecase.Result
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.notification.GetNotificationsViewsRepository
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.update.UpdateId
import app.meetacy.backend.types.serializable.update.type
import app.meetacy.backend.types.update.UpdateView
import kotlinx.coroutines.flow.map
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable

fun StreamUpdatesRepository(
    auth: AuthRepository,
    storage: StreamUpdatesUsecase.Storage,
    notificationsRepository: GetNotificationsViewsRepository
): StreamUpdatesRepository = UsecaseStreamUpdatesRepository(
    usecase = StreamUpdatesUsecase(auth, storage, notificationsRepository)
)

class UsecaseStreamUpdatesRepository(
    private val usecase: StreamUpdatesUsecase
) : StreamUpdatesRepository {

    override suspend fun flow(
        token: AccessIdentitySerializable,
        fromId: UpdateId?
    ): StreamUpdatesRepository.Result {
        return when (
            val result = usecase.flow(token.type(), fromId?.type())
        ) {
            is Result.Ready -> StreamUpdatesRepository.Result.Ready(
                flow = result.flow.map(UpdateView::mapToEndpoint)
            )
            is Result.TokenInvalid -> StreamUpdatesRepository.Result.TokenInvalid
        }
    }
}
