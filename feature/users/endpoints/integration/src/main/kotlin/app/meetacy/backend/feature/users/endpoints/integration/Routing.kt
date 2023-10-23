package app.meetacy.backend.feature.users.endpoints.integration

import app.meetacy.backend.feature.users.endpoints.integration.edit.editUser
import app.meetacy.backend.feature.users.endpoints.integration.get.getUser
import app.meetacy.backend.feature.users.endpoints.integration.username.username
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.users(di: DI) = route("/users") {
    username(di)
    getUser(di)
    editUser(di)
}
