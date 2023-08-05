package app.meetacy.backend.endpoint.types.user

import app.meetacy.backend.types.serializable.file.FileIdentity
import app.meetacy.backend.types.serializable.user.Username
import app.meetacy.backend.types.serializable.user.Relationship
import app.meetacy.backend.types.serializable.user.UserIdentity
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val isSelf: Boolean,
    val relationship: Relationship?,
    val id: UserIdentity,
    val nickname: String,
    val username: Username?,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarId: FileIdentity?
)
