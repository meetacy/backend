@file:UseSerializers(UserIdSerializer::class, AccessHashSerializer::class)

package app.meetacy.backend.endpoint.types

import app.meetacy.backend.domain.AccessHash
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.serialization.AccessHashSerializer
import app.meetacy.backend.serialization.UserIdSerializer
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
