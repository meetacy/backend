package app.meetacy.backend.endpoint.ktor.versioning

import io.ktor.server.application.*
import io.ktor.server.request.*

fun ApplicationCall.extractApiVersion(): ApiVersion? {
    val string = request.header(ApiVersion.Header) ?: return null
    return ApiVersion(int = string.toIntOrNull() ?: return null)
}
