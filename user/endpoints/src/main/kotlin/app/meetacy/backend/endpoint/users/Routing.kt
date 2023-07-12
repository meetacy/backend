package app.meetacy.backend.endpoint.users

import app.meetacy.backend.endpoint.users.edit.EditUserRepository
import app.meetacy.backend.endpoint.users.edit.editUser
import app.meetacy.backend.endpoint.users.get.UserRepository
import app.meetacy.backend.endpoint.users.get.getUser
import app.meetacy.backend.endpoint.users.username.UsernameDependencies
import app.meetacy.backend.endpoint.users.username.username
import io.ktor.server.routing.*

class UsersDependencies(
    val getUserRepository: UserRepository,
    val editUserRepository: EditUserRepository,
    val usernameDependencies: UsernameDependencies
)

fun Route.users(
    dependencies: UsersDependencies
) = route("/users") {
    getUser(dependencies.getUserRepository)
    editUser(dependencies.editUserRepository)
    username(dependencies.usernameDependencies)
}
