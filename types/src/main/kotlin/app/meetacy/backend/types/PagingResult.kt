package app.meetacy.backend.types

data class PagingResult<out T>(
    val nextPagingId: PagingId?,
    val data: T
) {
    inline fun <R> map(transform: (T) -> R): PagingResult<R> =
        PagingResult(nextPagingId, transform(data))
}
