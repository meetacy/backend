package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.AccessHashSerializable
import app.meetacy.backend.types.serialization.UserIdSerializable
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: UserIdSerializable,
    val accessHash: AccessHashSerializable,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?
)
