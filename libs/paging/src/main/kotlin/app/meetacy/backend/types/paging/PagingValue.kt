package app.meetacy.backend.types.paging

data class PagingValue<T>(
    val value: T,
    val nextPagingId: PagingId
) {
    inline fun <R> map(transform: (T) -> R): PagingValue<R> {
        return PagingValue(
            value = transform(value),
            nextPagingId = nextPagingId
        )
    }
}
