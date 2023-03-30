package app.meetacy.backend.endpoint.users.avatar.add

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
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
    val token: AccessIdentitySerializable,
    val fileId: FileIdentitySerializable
)

interface AddUserAvatarRepository {
    suspend fun addAvatar(addUserAvatarParams: AddUserAvatarParams): AddUserAvatarResult
}

fun Route.addUserAvatar(provider: AddUserAvatarRepository) = post("/add") {
    val params = call.receive<AddUserAvatarParams>()

    when (provider.addAvatar(params)) {

        is AddUserAvatarResult.Success -> call.respondSuccess()

        AddUserAvatarResult.InvalidIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
        AddUserAvatarResult.InvalidUserAvatarIdentity -> call.respondFailure(Failure.InvalidFileIdentity)
    }
}
