package app.meetacy.backend.types.paging.serializable

import kotlinx.serialization.Serializable

@Serializable
data class PagingResult<T>(
    val data: List<T>,
    val nextPagingId: PagingId?
)
