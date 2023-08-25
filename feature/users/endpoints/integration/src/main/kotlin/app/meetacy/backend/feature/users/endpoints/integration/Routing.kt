package app.meetacy.backend.feature.users.endpoints.integration

import app.meetacy.backend.feature.users.endpoints.integration.edit.editUser
import app.meetacy.backend.feature.users.endpoints.integration.get.getUser
import app.meetacy.backend.feature.users.endpoints.integration.validate.validate
import io.ktor.server.routing.*

fun Route.users() = route("/users") {
    getUser()
    editUser()
    validate()
}
