package app.meetacy.backend.types.serializable.file

import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.serializable.file.FileId as FileIdSerializable

fun FileIdSerializable.type() = FileId(long)
fun FileId.serializable() = FileIdSerializable(long)
