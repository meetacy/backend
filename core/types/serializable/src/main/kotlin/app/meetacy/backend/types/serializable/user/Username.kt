package app.meetacy.backend.types.serializable.user

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Username(val string: String)
