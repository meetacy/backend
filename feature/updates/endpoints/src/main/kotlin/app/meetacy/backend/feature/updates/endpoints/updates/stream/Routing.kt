package app.meetacy.backend.feature.updates.endpoints.updates.stream

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.rsocket.failRSocket
import app.meetacy.backend.types.serializable.update.Update
import app.meetacy.backend.feature.updates.endpoints.updates.stream.StreamUpdatesRepository.Result
import app.meetacy.backend.types.serializable.update.UpdateId
import io.ktor.server.routing.*
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.ktor.server.rSocket
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable

@Serializable
data class InitStreamUpdates(
    val token: AccessIdentitySerializable,
    val fromId: UpdateId?,
    val apiVersion: Int
)

interface StreamUpdatesRepository {
    suspend fun flow(
        token: AccessIdentitySerializable,
        fromId: UpdateId?
    ): Result

    sealed interface Result {
        data object TokenInvalid : Result
        class Ready(val flow: Flow<Update>) : Result
    }
}

fun Route.streamUpdates(
    repository: StreamUpdatesRepository
) = rSocket("/stream") {
    RSocketRequestHandler {
        requestStream { payload ->
            val initial = payload.decodeToInit()

            val result = with(initial) {
                repository.flow(token, fromId)
            }

            when (result) {
                is Result.Ready -> result.flow.map { update -> update.encodeToPayload() }
                is Result.TokenInvalid -> failRSocket(Failure.InvalidToken)
            }
        }
    }
}

private fun Payload.decodeToInit(): InitStreamUpdates =
    Json.decodeFromString(data.readText())

private fun Update.encodeToPayload() = buildPayload {
    data(Json.encodeToString(this@encodeToPayload))
}
