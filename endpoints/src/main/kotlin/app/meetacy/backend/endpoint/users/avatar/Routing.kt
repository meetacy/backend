package app.meetacy.backend.endpoint.users.avatar

import app.meetacy.backend.endpoint.users.avatar.add.AddUserAvatarRepository
import app.meetacy.backend.endpoint.users.avatar.add.addUserAvatar
import app.meetacy.backend.endpoint.users.avatar.delete.DeleteUserAvatarRepository
import app.meetacy.backend.endpoint.users.avatar.delete.deleteAvatar
import io.ktor.server.routing.*

class UserAvatarDependencies(
    val addUserAvatarRepository: AddUserAvatarRepository,
    val deleteUserAvatarRepository: DeleteUserAvatarRepository
)

fun Route.avatar(dependencies: UserAvatarDependencies) = route("/avatar") {
    addUserAvatar(dependencies.addUserAvatarRepository)
    deleteAvatar(dependencies.deleteUserAvatarRepository)
}