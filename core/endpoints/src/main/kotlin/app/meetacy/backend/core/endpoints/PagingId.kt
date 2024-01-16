package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.paging.serializable.PagingId
import io.ktor.server.application.*
import kotlinx.serialization.SerializationException
import app.meetacy.backend.types.serializable.serialization

fun ApplicationCall.pagingId(): PagingId = serialization {
    val pagingId = parameters["pagingId"]
    return if (pagingId == null) {
        throw SerializationException("Bad request. Illegal input: param 'pagingId' is required for type with serial name, but it was missing at path: $")
    } else PagingId(pagingId)
}
