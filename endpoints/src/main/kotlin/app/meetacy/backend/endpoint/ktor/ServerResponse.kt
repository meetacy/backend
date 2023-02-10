package app.meetacy.backend.endpoint.ktor

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

interface ServerResponse<out T> {
    val status: Boolean
    val result: T?
    val errorCode: Int?
    val errorMessage: String?

    @Serializable
    data class Success<out T>(
        override val status: Boolean,
        override val result: T
    ) : ServerResponse<T> {
        @Transient
        override val errorCode = null
        @Transient
        override val errorMessage = null
    }

    @Serializable
    data class Failure(
        override val status: Boolean,
        override val errorCode: Int,
        override val errorMessage: String
    ) : ServerResponse<Nothing> {
        @Transient
        override val result = null
    }
}
