package app.meetacy.backend.endpoint.ktor.rsocket

import app.meetacy.backend.endpoint.ktor.Failure
import io.rsocket.kotlin.RSocketError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val failureErrorCode = RSocketError.Custom.MinAllowedCode

fun failRSocket(failure: Failure): Nothing {
    throw RSocketError.Custom(
        errorCode = failureErrorCode,
        message = Json.encodeToString(failure)
    )
}
