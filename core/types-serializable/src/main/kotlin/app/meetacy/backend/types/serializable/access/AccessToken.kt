package app.meetacy.backend.types.serializable.access

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class AccessToken(val string: String)
