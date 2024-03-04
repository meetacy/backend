package app.meetacy.backend.core.endpoints

import app.meetacy.backend.types.serializable.users.UserIdentity
import io.ktor.server.application.*

fun ApplicationCall.userIdentity(): UserIdentity? {
    return parameters["id"]?.let { UserIdentity(it) }
}
