package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.FileId
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class FileIdSerializable(private val long: Long) {
    fun type() = FileId(long)
}

fun FileId.serializable() = FileIdSerializable(long)