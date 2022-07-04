package app.meetacy.backend.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthorization(
        val login: String,
        val password: String
)
