package app.meetacy.backend.endpoint.users.avatar.delete

import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

sealed interface DeleteUserAvatarResult {
    object Success : DeleteUserAvatarResult
    object InvalidAccessIdentity : DeleteUserAvatarResult
}

@Serializable
data class DeleteUserAvatarParams(
    val accessIdentity: AccessIdentitySerializable
)

@Serializable
data class DeleteUserAvatarResponse(
    val status: Boolean = false,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

interface DeleteUserAvatarRepository {
    suspend fun deleteAvatar(deleteUserAvatarParams: DeleteUserAvatarParams): DeleteUserAvatarResult
}

fun Route.deleteAvatar(provider: DeleteUserAvatarRepository) = post("/delete") {
    val params = call.receive<DeleteUserAvatarParams>()

    when(provider.deleteAvatar(params)) {
        is DeleteUserAvatarResult.Success -> call.respond(
            DeleteUserAvatarResponse(
                status = true,
                errorCode = null,
                errorMessage = null
            )
        )
        DeleteUserAvatarResult.InvalidAccessIdentity -> call.respond(
            DeleteUserAvatarResponse(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid identity"
            )
        )
    }
}
