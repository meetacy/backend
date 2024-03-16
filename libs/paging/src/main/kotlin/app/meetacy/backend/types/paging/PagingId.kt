package app.meetacy.backend.types.paging

@JvmInline
value class PagingId(val long: Long)

val PagingId?.longOrZero: Long get() = this?.long ?: 0
