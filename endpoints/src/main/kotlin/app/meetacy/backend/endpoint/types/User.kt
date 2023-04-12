package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.file.FileIdentitySerializable
import app.meetacy.backend.types.serialization.user.UserIdentitySerializable
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val isSelf: Boolean,
    val id: UserIdentitySerializable,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarIdentity: FileIdentitySerializable?
)
