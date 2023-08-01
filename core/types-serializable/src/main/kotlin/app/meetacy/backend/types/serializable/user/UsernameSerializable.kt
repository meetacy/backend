package app.meetacy.backend.types.serializable.user

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

fun Username.serializable() = UsernameSerializable(string)
