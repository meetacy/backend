package app.meetacy.backend.endpoint.users.avatar.add

import app.meetacy.backend.endpoint.ktor.respondEmptySuccess
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

sealed interface AddUserAvatarResult {
    object Success : AddUserAvatarResult
    object InvalidIdentity : AddUserAvatarResult
    object InvalidUserAvatarIdentity : AddUserAvatarResult
}

@Serializable
data class AddUserAvatarParams(
    val accessIdentity: AccessIdentitySerializable,
    val avatarIdentity: FileIdentitySerializable
)

interface AddUserAvatarRepository {
    suspend fun addAvatar(addUserAvatarParams: AddUserAvatarParams): AddUserAvatarResult
}

fun Route.addUserAvatar(provider: AddUserAvatarRepository) = post("/add") {
    val params = call.receive<AddUserAvatarParams>()

    when(provider.addAvatar(params)) {
        is AddUserAvatarResult.Success -> call.respondEmptySuccess()
        AddUserAvatarResult.InvalidIdentity -> call.respondFailure(
            1, "Please provide a valid identity"
        )
        AddUserAvatarResult.InvalidUserAvatarIdentity -> call.respondFailure(
            2, "Please provide a valid fileIdentity"
        )
    }
}
