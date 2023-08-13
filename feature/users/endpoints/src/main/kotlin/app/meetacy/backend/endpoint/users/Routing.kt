package app.meetacy.backend.endpoint.users

import app.meetacy.backend.endpoint.users.edit.editUser
import app.meetacy.backend.endpoint.users.get.getUser
import io.ktor.server.routing.*

fun Route.users() = route("/users") {
    getUser()
    editUser()
}
