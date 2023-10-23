package app.meetacy.backend.core.endpoints

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.types.serializable.access.AccessIdentity
import io.ktor.server.application.*
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun ApplicationCall.accessIdentity(): AccessIdentity {
    val identity = request.headers["Authorization"]
    return if (identity == null) {
        respondFailure(Failure.InvalidToken)
        throw CancellationException()
    } else AccessIdentity(identity)
}
