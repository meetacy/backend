package app.meetacy.backend.types.serializable.users

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class UserId(val long: Long)
