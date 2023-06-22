package app.meetacy.backend.usecase.integration.updates.stream

import app.meetacy.backend.endpoint.types.updates.Update
import app.meetacy.backend.endpoint.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.update.UpdateId
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetNotificationsViewsRepository
import app.meetacy.backend.usecase.updates.stream.StreamUpdatesUsecase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope

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
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun stream(
        token: AccessIdentitySerializable,
        fromId: UpdateId?,
        channel: SendChannel<Update>
    ) {
        coroutineScope {
            val usecaseChannel = produce {
                usecase.stream(token.type(), fromId, this.channel)
            }
            for (element in usecaseChannel) {
                channel.send(element.mapToEndpoint())
            }
        }
    }
}
