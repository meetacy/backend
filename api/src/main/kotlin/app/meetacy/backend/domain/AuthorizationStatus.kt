package app.meetacy.backend.domain

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationStatus(
    val status: Boolean
)
