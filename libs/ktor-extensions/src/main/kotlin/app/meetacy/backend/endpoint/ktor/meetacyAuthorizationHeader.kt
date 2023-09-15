package app.meetacy.backend.endpoint.ktor

import io.ktor.server.application.*

suspend inline fun ApplicationCall.accessIdentity(
    fallback: () -> Nothing
): String {
    return ""
}