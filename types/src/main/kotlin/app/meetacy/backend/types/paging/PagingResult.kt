package app.meetacy.backend.types.paging

data class PagingResult<out T>(
    val data: T,
    val nextPagingId: PagingId?
) {
    inline fun <R> map(transform: (T) -> R): PagingResult<R> =
        PagingResult(transform(data), nextPagingId)
}
