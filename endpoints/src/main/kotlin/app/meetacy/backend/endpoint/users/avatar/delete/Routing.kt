package app.meetacy.backend.endpoint.users.avatar.delete

import app.meetacy.backend.endpoint.ktor.respondEmptySuccess
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
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

interface DeleteUserAvatarRepository {
    suspend fun deleteAvatar(deleteUserAvatarParams: DeleteUserAvatarParams): DeleteUserAvatarResult
}

fun Route.deleteAvatar(provider: DeleteUserAvatarRepository) = post("/delete") {
    val params = call.receive<DeleteUserAvatarParams>()

    when(provider.deleteAvatar(params)) {
        is DeleteUserAvatarResult.Success -> call.respondEmptySuccess()
        DeleteUserAvatarResult.InvalidAccessIdentity -> call.respondFailure(
            1, "Please provide a valid identity"
        )
    }
}
