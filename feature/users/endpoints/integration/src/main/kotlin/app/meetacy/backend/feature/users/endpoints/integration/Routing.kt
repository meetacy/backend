package app.meetacy.backend.feature.users.endpoints.integration

import app.meetacy.backend.feature.users.endpoints.integration.edit.editUser
import app.meetacy.backend.feature.users.endpoints.integration.get.getUser
import app.meetacy.backend.feature.users.endpoints.integration.validate.validate
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.users(di: DI) = route("/users") {
    getUser(di)
    editUser(di)
    validate(di)
}
