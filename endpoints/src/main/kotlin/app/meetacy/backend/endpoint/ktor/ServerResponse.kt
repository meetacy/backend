package app.meetacy.backend.endpoint.ktor

import kotlinx.serialization.Serializable

@Serializable
data class Success<out T>(
    val status: Boolean,
    val result: T
)

@Serializable
data class Failure(
    val status: Boolean,
    val errorCode: Int,
    val errorMessage: String
)

@Serializable
data class EmptySuccess(
    val status: Boolean
)
