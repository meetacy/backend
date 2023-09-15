package app.meetacy.backend.endpoint.ktor

import io.ktor.server.application.*

suspend inline fun ApplicationCall.accessIdentity(
    fallback: () -> Nothing
): String {
    val identity = request.headers["Authorization"]
    return if (identity == null) {
        respondFailure(Failure.InvalidToken)
        fallback()
    } else identity
}
