package app.meetacy.backend.feature.usecase.integration.updates.stream

import app.meetacy.backend.endpoint.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.feature.auth.usecase.types.AuthRepository
import app.meetacy.backend.feature.auth.usecase.types.GetNotificationsViewsRepository
import app.meetacy.backend.feature.auth.usecase.types.UpdateView
import app.meetacy.backend.feature.auth.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.update.UpdateId
import app.meetacy.backend.types.serializable.update.type
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
            is StreamUpdatesUsecase.Result.Ready -> StreamUpdatesRepository.Result.Ready(
                flow = result.flow.map(UpdateView::mapToEndpoint)
            )
            is StreamUpdatesUsecase.Result.TokenInvalid -> StreamUpdatesRepository.Result.TokenInvalid
        }
    }
}
