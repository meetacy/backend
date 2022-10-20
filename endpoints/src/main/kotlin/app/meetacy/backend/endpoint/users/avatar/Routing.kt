package app.meetacy.backend.endpoint.users.avatar

import app.meetacy.backend.endpoint.users.avatar.add.AddAvatarRepository
import app.meetacy.backend.endpoint.users.avatar.add.addAvatar
import io.ktor.server.routing.*

class AvatarDependencies(
    val addAvatarRepository: AddAvatarRepository,
    //val deleteAvatarRepository: DeleteAvatarRepository
)

fun Route.avatar(dependencies: AvatarDependencies) = route("/avatar") {
    addAvatar(dependencies.addAvatarRepository)
    //deleteAvatar(dependencies.deleteAvatarRepository)
}