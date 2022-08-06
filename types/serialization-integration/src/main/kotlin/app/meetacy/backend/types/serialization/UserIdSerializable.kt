package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.UserId
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
@JvmInline
value class UserIdSerializable(private val long: Long) {
    fun type() = UserId(long)
}

fun UserId.serializable() = UserIdSerializable(long)
