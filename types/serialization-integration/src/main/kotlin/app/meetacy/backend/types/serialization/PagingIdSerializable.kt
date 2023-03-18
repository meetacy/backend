package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.PagingId
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class PagingIdSerializable(private val string: String) {
    fun type() = PagingId(string.toLong())
}

fun PagingId.serializable() = PagingIdSerializable(long.toString())
