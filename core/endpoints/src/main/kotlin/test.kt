import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.types.serializable.access.AccessIdentity
import io.ktor.server.application.*

suspend inline fun ApplicationCall.accessIdentity(
    fallback: () -> Nothing
): AccessIdentity {
    val identity = request.headers["Authorization"]
    return if (identity == null) {
        respondFailure(Failure.InvalidToken)
        fallback()
    } else AccessIdentity(identity)
}
