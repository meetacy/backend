package app.meetacy.backend.endpoint.friends.location.stream

import app.meetacy.backend.endpoint.types.UserOnMap
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.location.LocationSerializable
import io.ktor.server.routing.*
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.ktor.server.rSocket
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class InitStreamLocation(
    val token: AccessIdentitySerializable
)

@Serializable
data class ProvideSelfLocation(
    val location: LocationSerializable
)

interface StreamLocationRepository {
    suspend fun stream(
        accessIdentity: AccessIdentity,
        selfLocation: Flow<Location>,
        collector: FlowCollector<UserOnMap>
    )
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

            flow {
                repository.stream(
                    accessIdentity = initial.token.type(),
                    selfLocation = selfLocation,
                    collector = this
                )
            }.map { user -> user.encodeToPayload() }
        }
    }
}

private fun Payload.decodeToInit(): InitStreamLocation =
    Json.decodeFromString(data.readText())

private fun Payload.decodeToSelfLocation(): ProvideSelfLocation =
    Json.decodeFromString(data.readText())

private fun UserOnMap.encodeToPayload(): Payload = buildPayload {
    data(Json.encodeToString(value = this@encodeToPayload))
}
