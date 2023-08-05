package app.meetacy.backend.types.serializable.user


import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class UserIdentity(val identity: String)
