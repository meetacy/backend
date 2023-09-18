package app.meetacy.backend.types.serializable.file

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class FileId(val long: Long)
