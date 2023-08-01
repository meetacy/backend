package app.meetacy.backend.endpoint.friends.location.stream

import app.meetacy.backend.endpoint.friends.location.stream.StreamLocationRepository.Result
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.rsocket.failRSocket
import app.meetacy.backend.endpoint.types.user.UserLocationSnapshot
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.location.type
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
import app.meetacy.backend.types.serializable.location.Location as LocationSerializable

@Serializable
data class InitStreamLocation(
    val token: AccessIdentitySerializable,
    val apiVersion: Int
)

@Serializable
data class ProvideSelfLocation(
    val location: LocationSerializable
)

interface StreamLocationRepository {
    suspend fun flow(
        accessIdentity: AccessIdentity,
        selfLocation: Flow<Location>
    ): Result

    sealed interface Result {
        data object TokenInvalid : Result
        class Ready(val flow: Flow<UserLocationSnapshot>) : Result
    }
}

fun Route.streamFriendsLocation(
    repository: StreamLocationRepository
) = rSocket("/stream") {
    RSocketRequestHandler {
        requestChannel { initialPayload, flow ->
            val initial = initialPayload.decodeToInit()

            val selfLocation = flow.map { payload ->
                payload.decodeToSelfLocation().location.type()
            }

            val result = repository.flow(
                accessIdentity = initial.token.type(),
                selfLocation = selfLocation
            )

            when (result) {
                is Result.Ready -> result.flow.map { user -> user.encodeToPayload() }
                is Result.TokenInvalid -> failRSocket(Failure.InvalidToken)
            }
        }
    }
}

private fun Payload.decodeToInit(): InitStreamLocation =
    Json.decodeFromString(data.readText())

private fun Payload.decodeToSelfLocation(): ProvideSelfLocation =
    Json.decodeFromString(data.readText())

private fun UserLocationSnapshot.encodeToPayload(): Payload = buildPayload {
    data(Json.encodeToString(value = this@encodeToPayload))
}
