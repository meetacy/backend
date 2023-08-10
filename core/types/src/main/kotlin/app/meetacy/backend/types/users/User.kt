package app.meetacy.backend.types.users

import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity


data class FullUser(
    val identity: UserIdentity,
    val nickname: String,
    val username: Username?,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarId: FileId?
)

data class UserView(
    val isSelf: Boolean,
    val relationship: Relationship?,
    val identity: UserIdentity,
    val nickname: String,
    val username: Username?,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarIdentity: FileIdentity?
)

