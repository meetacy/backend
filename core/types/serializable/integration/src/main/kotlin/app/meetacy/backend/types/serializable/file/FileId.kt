package app.meetacy.backend.types.serializable.file

import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.file.FileId as FileIdSerializable

fun FileIdSerializable.type() = serialization { FileId(long) }
fun FileId.serializable() = FileIdSerializable(long)
