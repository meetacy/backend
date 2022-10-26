package app.meetacy.backend.endpoint.users.avatar.add

import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
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

@Serializable
data class AddUserAvatarResponse(
    val status: Boolean = false,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

interface AddUserAvatarRepository {
    suspend fun addAvatar(addUserAvatarParams: AddUserAvatarParams): AddUserAvatarResult
}

fun Route.addUserAvatar(provider: AddUserAvatarRepository) = post("/add") {
    val params = call.receive<AddUserAvatarParams>()

    when(provider.addAvatar(params)) {
        is AddUserAvatarResult.Success -> call.respond(
            AddUserAvatarResponse(
                status = true,
                errorCode = null,
                errorMessage = null
            )
        )
        AddUserAvatarResult.InvalidIdentity -> call.respond(
            AddUserAvatarResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid identity"
            )
        )
        AddUserAvatarResult.InvalidUserAvatarIdentity -> call.respond(
            AddUserAvatarResponse(
                status = false,
                errorCode = 2,
                errorMessage = "Please provide a valid fileIdentity"
            )
        )
    }
}