package app.meetacy.backend.feature.updates.endpoints.integration.stream

import app.meetacy.backend.feature.updates.endpoints.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.feature.updates.endpoints.updates.stream.streamUpdates
import app.meetacy.backend.feature.updates.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.update.UpdateId
import app.meetacy.backend.types.serializable.update.mapToEndpoint
import app.meetacy.backend.types.serializable.update.type
import app.meetacy.backend.types.update.UpdateView
import app.meetacy.di.DI
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.map

internal fun Route.streamUpdates(di: DI) {
    val streamUpdatesUsecase: StreamUpdatesUsecase by di.getting

    val repository = object : StreamUpdatesRepository {
        override suspend fun flow(
            token: AccessIdentity,
            fromId: UpdateId?
        ): StreamUpdatesRepository.Result {
            return when (
                val result = streamUpdatesUsecase.flow(token.type(), fromId?.type())
            ) {
                is StreamUpdatesUsecase.Result.Ready -> StreamUpdatesRepository.Result.Ready(
                    flow = result.flow.map(UpdateView::mapToEndpoint)
                )
                is StreamUpdatesUsecase.Result.TokenInvalid -> StreamUpdatesRepository.Result.TokenInvalid
            }
        }
    }

    streamUpdates(repository)
}
