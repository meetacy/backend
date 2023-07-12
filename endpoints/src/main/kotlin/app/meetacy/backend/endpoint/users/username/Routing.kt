package app.meetacy.backend.endpoint.users.username

import app.meetacy.backend.endpoint.users.username.validate.ValidateUsernameRepository
import app.meetacy.backend.endpoint.users.username.validate.validateUsername
import io.ktor.server.routing.*

class UsernameDependencies(
    val validateUsernameRepository: ValidateUsernameRepository
)

fun Route.username(dependencies: UsernameDependencies) = route("/username") {
    validateUsername(dependencies.validateUsernameRepository)
}
