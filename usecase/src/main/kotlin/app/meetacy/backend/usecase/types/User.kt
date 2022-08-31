package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.UserIdentity


data class FullUser(
    val identity: UserIdentity,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?
)

data class UserView(
    val identity: UserIdentity,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?
)
