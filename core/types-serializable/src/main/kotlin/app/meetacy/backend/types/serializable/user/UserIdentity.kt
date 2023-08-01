package app.meetacy.backend.types.serializable.user

import app.meetacy.backend.types.user.UserIdentity
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class UserIdentitySerializable(val identity: String) {
    init {
        // will crash while serializing if the identity is invalid
        type()
    }
    fun type() = UserIdentity.parse(identity)!!
}

fun UserIdentity.serializable() = UserIdentitySerializable(string)
