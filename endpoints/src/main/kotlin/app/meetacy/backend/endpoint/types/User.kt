package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.AccessHashSerializable
import app.meetacy.backend.types.serialization.UserIdSerializable
import app.meetacy.backend.types.serialization.UserIdentitySerializable
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val identity: UserIdentitySerializable,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?
)
