package app.meetacy.backend.types.serializable.users


import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class UserId(val string: String)
