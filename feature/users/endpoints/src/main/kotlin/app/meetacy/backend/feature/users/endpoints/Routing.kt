package app.meetacy.backend.feature.users.endpoints

import app.meetacy.backend.feature.users.endpoints.edit.editUser
import app.meetacy.backend.feature.users.endpoints.get.getUser
import io.ktor.server.routing.*

fun Route.users() = route("/users") {
    getUser()
    editUser()
}
