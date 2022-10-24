package app.meetacy.backend.endpoint.users.avatar

import app.meetacy.backend.endpoint.users.avatar.add.AddUserAvatarRepository
import app.meetacy.backend.endpoint.users.avatar.add.addUserAvatar
import io.ktor.server.routing.*

class UserAvatarDependencies(
    val addUserAvatarRepository: AddUserAvatarRepository,
    //val deleteAvatarRepository: DeleteAvatarRepository
)

fun Route.avatar(dependencies: UserAvatarDependencies) = route("/avatar") {
    addUserAvatar(dependencies.addUserAvatarRepository)
    //deleteAvatar(dependencies.deleteAvatarRepository)
}