package app.meetacy.backend.feature.updates.endpoints.integration

import app.meetacy.backend.feature.updates.endpoints.integration.stream.streamUpdates
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
    streamUpdates()
}
