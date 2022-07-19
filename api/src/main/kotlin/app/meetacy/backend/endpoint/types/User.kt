@file:UseSerializers(UserIdSerializer::class)

package app.meetacy.backend.endpoint.types

import app.meetacy.backend.domain.UserId
import app.meetacy.backend.serialization.UserIdSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class User(
    val id: UserId,
    val accessHash: String,
    val nickname: String
)
