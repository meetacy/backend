package app.meetacy.backend.types.serializable.file

import app.meetacy.backend.types.files.FileSize
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.file.FileSize as FileSizeSerializable

fun FileSizeSerializable.type() = serialization { FileSize(bytesSize) }
fun FileSize.serializable() = FileSizeSerializable(bytesSize)
