package app.meetacy.backend.endpoint.users.avatar.add

import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

sealed interface AddAvatarResult {
    object Success : AddAvatarResult
    object InvalidIdentity : AddAvatarResult
    object InvalidAvatarIdentity : AddAvatarResult
}

@Serializable
data class AddAvatarParams(
    val accessIdentity: AccessIdentitySerializable,
    val avatarIdentity: FileIdentitySerializable
)

@Serializable
data class AddAvatarResponse(
    val status: Boolean = false,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

interface AddAvatarRepository {
    suspend fun addAvatar(addAvatarParams: AddAvatarParams): AddAvatarResult
}

fun Route.addAvatar(provider: AddAvatarRepository) = post("/add") {
    val params = call.receive<AddAvatarParams>()

    when(with(params) { provider.addAvatar(AddAvatarParams(accessIdentity, avatarIdentity)) }) {
        is AddAvatarResult.Success -> call.respond(
            AddAvatarResponse(
                status = true,
                errorCode = null,
                errorMessage = null
            )
        )
        AddAvatarResult.InvalidIdentity -> call.respond(
            AddAvatarResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid identity"
            )
        )
        AddAvatarResult.InvalidAvatarIdentity -> call.respond(
            AddAvatarResponse(
                status = false,
                errorCode = 2,
                errorMessage = "Please provide a valid fileIdentity"
            )
        )
    }
}