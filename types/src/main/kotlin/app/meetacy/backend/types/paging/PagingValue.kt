package app.meetacy.backend.types.paging

data class PagingValue<T>(
    val value: T,
    val nextPagingId: PagingId
)
