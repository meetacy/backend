package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.users.UserId
import io.ktor.http.*
import kotlinx.serialization.SerializationException

fun Parameters.userIdOrNull(name: String = "userId"): UserId? {
    return this[name]?.let(::UserId)
}

fun Parameters.userId(name: String = "userId"): UserId = serialization {
    val userId = this[name] ?: throw SerializationException("Bad request. Illegal input: param 'userId' is required for type with serial name, but it was missing at path: $name")
    UserId(userId)
}
