package app.meetacy.backend.types.serialization.user

import app.meetacy.backend.types.user.UserId
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class UserIdSerializable(private val long: Long) {
    fun type() = UserId(long)
}

fun UserId.serializable() = UserIdSerializable(long)
