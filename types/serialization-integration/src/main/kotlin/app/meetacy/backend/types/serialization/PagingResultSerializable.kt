package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.PagingResult
import kotlinx.serialization.Serializable

@Serializable
data class PagingResultSerializable<T>(
    val nextPagingId: PagingIdSerializable?,
    val data: T
) {
    fun type() = PagingResult(nextPagingId?.type(), data)
}

fun <T> PagingResult<T>.serializable() =
    PagingResultSerializable(nextPagingId?.serializable(), data)
