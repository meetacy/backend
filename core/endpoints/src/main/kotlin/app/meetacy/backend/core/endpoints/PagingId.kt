package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.serializable.serialization
import io.ktor.server.application.*

fun ApplicationCall.pagingId(): PagingId? = serialization {
    val pagingId = parameters["pagingId"]
    return if (pagingId == null) {
        null
    } else PagingId(pagingId)
}
