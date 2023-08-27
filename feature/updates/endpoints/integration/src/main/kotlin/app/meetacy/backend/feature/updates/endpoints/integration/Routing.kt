package app.meetacy.backend.feature.updates.endpoints.integration

import app.meetacy.backend.feature.updates.endpoints.updates.stream.StreamUpdatesRepository
import app.meetacy.backend.feature.updates.usecase.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.update.UpdateId
import app.meetacy.backend.types.serializable.update.mapToEndpoint
import app.meetacy.backend.types.serializable.update.type
import app.meetacy.backend.types.update.UpdateView
import app.meetacy.di.global.di
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.map

fun Route.updates() {
    val repository = object : StreamUpdatesRepository {
        val usecase: StreamUpdatesUsecase by di.getting
        override suspend fun flow(
            token: AccessIdentity,
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
}
