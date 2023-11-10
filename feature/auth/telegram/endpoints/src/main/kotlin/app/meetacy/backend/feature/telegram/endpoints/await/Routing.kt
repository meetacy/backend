package app.meetacy.backend.feature.telegram.endpoints.await

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.Success
import app.meetacy.backend.endpoint.ktor.buildSuccessPayload
import app.meetacy.backend.endpoint.ktor.rsocket.failRSocket
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.AccessToken
import io.ktor.server.routing.Route
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.ktor.server.rSocket
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class AwaitParams(
    // We do not specify user id, because there is
    // no user yet, so AccessToken is used here,
    // not AccessIdentity
    val temporalToken: AccessToken,
    val apiVersion: Int
)

interface AwaitRepository {
    suspend fun await(
        token: AccessToken
    ): AwaitResult
}

sealed interface AwaitResult {
    data object TokenInvalid : AwaitResult
    data class Success(val permanentToken: AccessIdentity) : AwaitResult
}

fun Route.telegramAwait(
    repository: AwaitRepository
) = rSocket("/await") {
    RSocketRequestHandler {
        requestResponse { payload ->
            val initial = payload.decodeToInit()
            when (val result = repository.await(initial.temporalToken)) {
                is AwaitResult.Success -> buildSuccessPayload(result.permanentToken)
                is AwaitResult.TokenInvalid -> failRSocket(Failure.InvalidToken)
            }
        }
    }
}

private fun Payload.decodeToInit(): AwaitParams =
    Json.decodeFromString(data.readText())
