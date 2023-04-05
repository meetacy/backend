package app.meetacy.backend.types.serialization.access

import app.meetacy.backend.types.access.AccessIdentity
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class AccessIdentitySerializable(val string: String) {
    init {
        type()
    }

    fun type() = AccessIdentity.parse(string)!!
}

fun AccessIdentity.serializable() = AccessIdentitySerializable(string)
