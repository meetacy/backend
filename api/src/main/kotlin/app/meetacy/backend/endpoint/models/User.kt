package app.meetacy.backend.endpoint.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val accessHash: String,
    val nickname: String
)
