package app.meetacy.backend.types.serialization.paging

import app.meetacy.backend.types.paging.PagingResult
import kotlinx.serialization.Serializable

@Serializable
data class PagingResultSerializable<T>(
    val nextPagingId: PagingIdSerializable?,
    val data: T
) {
    fun type() = PagingResult(data, nextPagingId?.type())
}

fun <T> PagingResult<T>.serializable() =
    PagingResultSerializable(nextPagingId?.serializable(), data)
