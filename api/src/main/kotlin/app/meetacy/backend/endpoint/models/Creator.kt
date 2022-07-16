package app.meetacy.backend.endpoint.models

import kotlinx.serialization.Serializable

@Serializable
data class Creator(
    val id: Long,
    val accessHash: String,
    val nickname: String
)
