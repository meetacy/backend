package app.meetacy.backend.types.serializable.access

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class AccessIdentity(val string: String)
