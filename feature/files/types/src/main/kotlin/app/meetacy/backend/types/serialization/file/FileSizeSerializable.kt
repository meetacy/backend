package app.meetacy.backend.types.serialization.file

import app.meetacy.backend.types.file.FileSize
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class FileSizeSerializable(private val long: Long) {
    fun type() = FileSize(long)
}

fun FileSize.serializable() = FileSizeSerializable(bytesSize)
