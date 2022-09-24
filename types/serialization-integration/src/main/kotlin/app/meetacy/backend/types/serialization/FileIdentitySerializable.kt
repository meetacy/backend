package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.FileIdentity
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class FileIdentitySerializable(val string: String) {
    init {
        type()
    }
    fun type() = FileIdentity.parse(string)!!
}

fun FileIdentity.serializable() = FileIdentitySerializable(string)