package app.meetacy.backend.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val fullname: String?,
    val email: String,
    val bio: String
)
