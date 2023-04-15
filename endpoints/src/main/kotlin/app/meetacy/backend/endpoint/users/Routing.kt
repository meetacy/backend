package app.meetacy.backend.endpoint.users

import app.meetacy.backend.endpoint.users.avatar.UserAvatarDependencies
import app.meetacy.backend.endpoint.users.avatar.avatar
import app.meetacy.backend.endpoint.users.edit.EditUserRepository
import app.meetacy.backend.endpoint.users.edit.editUser
import app.meetacy.backend.endpoint.users.get.UserRepository
import app.meetacy.backend.endpoint.users.get.getUser
import io.ktor.server.routing.*

class UsersDependencies(
    val getUserRepository: UserRepository,
    val addUserAvatarDependencies: UserAvatarDependencies,
    val editUserRepository: EditUserRepository
)

fun Route.users(
    dependencies: UsersDependencies
) = route("/users") {
    getUser(dependencies.getUserRepository)
    avatar(dependencies.addUserAvatarDependencies)
    editUser(dependencies.editUserRepository)
}