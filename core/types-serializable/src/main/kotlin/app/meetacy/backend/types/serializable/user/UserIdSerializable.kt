package app.meetacy.backend.types.serializable.user

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class UserIdSerializable(val long: Long)
