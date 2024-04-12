package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.users.UserId
import io.ktor.http.*
import kotlinx.serialization.SerializationException


fun Parameters.friendIdOrNull(name: String = "friendId"): UserId? {
    return this[name]?.let(::UserId)
}

fun Parameters.friendId(name: String = "friendId"): UserId = serialization {
    val friendId = this[name] ?: throw SerializationException("Bad request. Illegal input: param 'friendId' is required for type with serial name, but it was missing at path: $name")
    UserId(friendId)
}
