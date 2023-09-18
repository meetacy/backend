package app.meetacy.backend.types.serializable.users

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Username(val string: String)
