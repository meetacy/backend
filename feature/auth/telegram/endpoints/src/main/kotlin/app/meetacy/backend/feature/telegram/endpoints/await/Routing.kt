package app.meetacy.backend.feature.telegram.endpoints.await

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.rsocket.failRSocket
import app.meetacy.backend.feature.telegram.endpoints.await.AwaitRepository.Result
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.AccessToken
import io.ktor.server.routing.*
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.ktor.server.rSocket
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.Deferred
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

@Serializable
data class AwaitResult(val token: AccessIdentity)

interface AwaitRepository {
    suspend fun async(
        token: AccessToken
    ): Result

    sealed interface Result {
        data object TokenInvalid : Result
        class Ready(val result: Deferred<AwaitResult>) : Result
    }
}

fun Routing.await(
    repository: AwaitRepository
) = rSocket("/await") {
    RSocketRequestHandler {
        requestResponse { payload ->
            val initial = payload.decodeToInit()
            when (val deferred = repository.async(initial.temporalToken)) {
                is Result.Ready -> deferred.result.await().encodeToPayload()
                is Result.TokenInvalid -> failRSocket(Failure.InvalidToken)
            }
        }
    }
}

private fun Payload.decodeToInit(): AwaitParams =
    Json.decodeFromString(data.readText())

private fun AwaitResult.encodeToPayload() = buildPayload {
    data(Json.encodeToString(this@encodeToPayload))
}
