package app.meetacy.backend.types.serialization.file

import app.meetacy.backend.types.file.FileId
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class FileIdSerializable(private val long: Long) {
    fun type() = FileId(long)
}

fun FileId.serializable() = FileIdSerializable(long)