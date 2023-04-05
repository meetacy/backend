package app.meetacy.backend.types.serialization.file

import app.meetacy.backend.types.file.FileIdentity
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