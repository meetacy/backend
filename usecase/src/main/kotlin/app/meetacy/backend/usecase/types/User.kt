package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.user.UserIdentity


data class FullUser(
    val identity: UserIdentity,
    val nickname: String,
    val username: String?,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarId: FileId?
)

data class UserView(
    val isSelf: Boolean,
    val identity: UserIdentity,
    val nickname: String,
    val username: String?,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarIdentity: FileIdentity?
)
