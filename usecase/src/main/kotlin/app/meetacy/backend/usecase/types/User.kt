package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.types.UserIdentity


data class FullUser(
    val identity: UserIdentity,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarIdentity: FileIdentity?
)

data class UserView(
    val identity: UserIdentity,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?,
    val avatarIdentity: FileIdentity?
)
