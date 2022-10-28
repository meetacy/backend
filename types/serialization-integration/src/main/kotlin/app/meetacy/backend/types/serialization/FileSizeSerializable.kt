package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.FileSize
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class FileSizeSerializable(private val long: Long) {
    fun type() = FileSize(long)
}

fun FileSize.serializable() = FileSizeSerializable(bytesSize)
