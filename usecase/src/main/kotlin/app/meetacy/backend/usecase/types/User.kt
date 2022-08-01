package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.UserId


data class FullUser(
    val id: UserId,
    val accessHash: AccessHash,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?
)

data class UserView(
    val id: UserId,
    val accessHash: AccessHash,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?
)
