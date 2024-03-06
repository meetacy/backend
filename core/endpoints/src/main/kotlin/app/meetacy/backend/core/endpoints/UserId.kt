package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.users.UserId
import io.ktor.http.*
import io.ktor.server.application.*

fun Parameters.userIdOrNull(name: String = "userId"): UserId? {
    return this[name]?.let(::UserId)
}
