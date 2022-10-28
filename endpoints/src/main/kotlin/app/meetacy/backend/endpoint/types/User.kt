package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.FileIdentitySerializable
import app.meetacy.backend.types.serialization.UserIdentitySerializable
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val identity: UserIdentitySerializable,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarIdentity: FileIdentitySerializable?
)
