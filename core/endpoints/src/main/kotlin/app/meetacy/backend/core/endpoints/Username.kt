package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.users.Username
import io.ktor.http.*

fun Parameters.usernameOrNull(name: String = "username"): Username? {
    return this[name]?.let(::Username)
}
