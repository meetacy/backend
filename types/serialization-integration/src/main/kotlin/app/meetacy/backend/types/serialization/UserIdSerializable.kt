package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class UserIdSerializable(private val long: Long) {
    fun type() = UserId(long)
}

fun UserId.serializable() = UserIdSerializable(long)
