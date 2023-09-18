package app.meetacy.backend.types.paging

data class PagingResult<out T>(
    val data: List<T>,
    val nextPagingId: PagingId?
) {
    inline fun <R> map(transform: (List<T>) -> List<R>): PagingResult<R> =
        PagingResult(transform(data), nextPagingId)

    inline fun <R> mapItems(transform: (T) -> R): PagingResult<R> =
        PagingResult(data.map(transform), nextPagingId)
}
