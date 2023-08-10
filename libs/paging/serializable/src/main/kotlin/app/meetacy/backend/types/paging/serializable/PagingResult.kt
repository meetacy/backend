package app.meetacy.backend.types.paging.serializable

import kotlinx.serialization.Serializable

@Serializable
data class PagingResult<T>(
    val nextPagingId: PagingId?,
    val data: List<T>
)
