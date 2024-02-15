package app.meetacy.backend.types.paging

import app.meetacy.backend.types.amount.Amount


inline fun <T> List<T>.pagingId(amount: Amount, getter: (T) -> PagingId): PagingId? {
    return if (amount.int == size) getter(last()) else null
}

inline fun <T> List<T>.pagingIdLong(amount: Amount, getter: (T) -> Long): PagingId? {
    return pagingId(amount) { PagingId(getter(it)) }
}

inline fun <T> List<T>.pagingResult(amount: Amount, getter: (T) -> PagingId): PagingResult<T> {
    return PagingResult(data = this, nextPagingId = pagingId(amount, getter))
}

fun <T> List<PagingValue<T>>.pagingResult(amount: Amount): PagingResult<T> {
    return pagingResult(amount) { paging -> paging.nextPagingId }
        .mapItems { paging -> paging.value }
}

inline fun <T> List<T>.pagingResultLong(amount: Amount, getter: (T) -> Long): PagingResult<T> {
    return PagingResult(data = this, nextPagingId = pagingIdLong(amount, getter))
}
