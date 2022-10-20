package app.meetacy.backend.endpoint.users

import app.meetacy.backend.endpoint.users.avatar.AvatarDependencies
import app.meetacy.backend.endpoint.users.avatar.avatar
import app.meetacy.backend.endpoint.users.get.UserRepository
import app.meetacy.backend.endpoint.users.get.getUser
import io.ktor.server.routing.*

class UsersDependencies(
    val getUserRepository: UserRepository,
    val addAvatarDependencies: AvatarDependencies
)

fun Route.users(
    dependencies: UsersDependencies
) = route("/users") {
    getUser(dependencies.getUserRepository)
    avatar(dependencies.addAvatarDependencies)
}