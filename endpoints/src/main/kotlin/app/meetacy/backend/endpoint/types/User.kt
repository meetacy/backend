@file:UseSerializers(UserIdSerializer::class, AccessHashSerializer::class)

package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.serialization.AccessHashSerializer
import app.meetacy.backend.types.serialization.UserIdSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class User(
    val id: UserId,
    val accessHash: AccessHash,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?
)
