package app.meetacy.backend.endpoint.types.user

import app.meetacy.backend.types.serializable.user.RelationshipSerializable
import app.meetacy.backend.types.serializable.user.UserIdentitySerializable
import app.meetacy.backend.types.serializable.user.UsernameSerializable
import app.meetacy.backend.types.serialization.file.FileIdentitySerializable
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val isSelf: Boolean,
    val relationship: RelationshipSerializable?,
    val id: UserIdentitySerializable,
    val nickname: String,
    val username: UsernameSerializable?,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarId: FileIdentitySerializable?
)
