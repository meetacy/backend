package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.serializable.serialization
import io.ktor.http.*

fun Parameters.pagingIdOrNull(name: String = "pagingId"): PagingId? = serialization {
    return this[name]?.let(::PagingId)
}
