package app.meetacy.backend.endpoint.users

import app.meetacy.backend.endpoint.users.edit.EditUserRepository
import app.meetacy.backend.endpoint.users.edit.editUser
import app.meetacy.backend.endpoint.users.get.UserRepository
import app.meetacy.backend.endpoint.users.get.getUser
import io.ktor.server.routing.*

class UsersDependencies(
    val getUserRepository: UserRepository,
    val editUserRepository: EditUserRepository
)

fun Route.users(
    dependencies: UsersDependencies
) = route("/users") {
    getUser(dependencies.getUserRepository)
    editUser(dependencies.editUserRepository)
}