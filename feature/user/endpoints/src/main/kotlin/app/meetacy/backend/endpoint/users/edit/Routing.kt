package app.meetacy.backend.endpoint.users.edit

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.user.User
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.optional.Optional
import app.meetacy.backend.types.serializable.user.UsernameSerializable
import app.meetacy.backend.types.serialization.file.FileIdentity
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class EditUserParams(
    val token: AccessIdentity,
    val nickname: Optional<String> = Optional.Undefined,
    val username: Optional<UsernameSerializable?> = Optional.Undefined,
    val avatarId: Optional<FileIdentity?> = Optional.Undefined,
)

sealed interface EditUserResult {
    class Success(val user: User) : EditUserResult
    data object InvalidAccessIdentity : EditUserResult
    data object InvalidUtf8String : EditUserResult
    data object NullEditParameters : EditUserResult
    data object InvalidAvatarIdentity : EditUserResult
    data object UsernameAlreadyOccupied : EditUserResult
}

interface EditUserRepository {
    suspend fun editUser(editUserParams: EditUserParams): EditUserResult
}

fun Route.editUser(editUserRepository: EditUserRepository) = post("/edit") {
    val params = call.receive<EditUserParams>()

    when (val result = editUserRepository.editUser(params)) {
        is EditUserResult.Success -> call.respondSuccess(result.user)
        EditUserResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidToken)
        EditUserResult.InvalidUtf8String -> call.respondFailure(Failure.InvalidUtf8String)
        EditUserResult.InvalidAvatarIdentity -> call.respondFailure(Failure.InvalidFileIdentity)
        EditUserResult.NullEditParameters -> call.respondFailure(Failure.NullEditParams)
        EditUserResult.UsernameAlreadyOccupied -> call.respondFailure(Failure.UsernameAlreadyOccupied)
    }
}
