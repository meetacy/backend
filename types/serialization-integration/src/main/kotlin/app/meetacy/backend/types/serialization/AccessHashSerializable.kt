package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.AccessHash
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
@JvmInline
value class AccessHashSerializable(private val string: String) {
    fun type() = AccessHash(string)
}

fun AccessHash.serializable() = AccessHashSerializable(string)
