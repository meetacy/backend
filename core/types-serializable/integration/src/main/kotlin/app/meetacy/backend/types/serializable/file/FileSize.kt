package app.meetacy.backend.types.serializable.file

import app.meetacy.backend.types.file.FileSize
import app.meetacy.backend.types.serializable.file.FileSize as FileSizeSerializable

fun FileSizeSerializable.type() = FileSize(bytesSize)

fun FileSize.serializable() = FileSizeSerializable(bytesSize)
