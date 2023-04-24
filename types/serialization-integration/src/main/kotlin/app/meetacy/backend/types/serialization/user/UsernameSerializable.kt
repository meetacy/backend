package app.meetacy.backend.types.serialization.user

import app.meetacy.backend.types.user.Username
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class UsernameSerializable(val string: String) {
    init {
        type()
    }
    fun type() = Username.parse(string)
}

fun UsernameSerializable.serializable() = UsernameSerializable(string)
