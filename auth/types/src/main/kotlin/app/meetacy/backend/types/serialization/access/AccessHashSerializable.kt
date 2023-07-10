package app.meetacy.backend.types.serialization.access

import app.meetacy.backend.types.access.AccessHash
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class AccessHashSerializable(private val string: String) {
    fun type() = AccessHash(string)
}

fun AccessHash.serializable() = AccessHashSerializable(string)
