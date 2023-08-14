package app.meetacy.backend.feature.users.endpoints.edit

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.file.FileIdentity
import app.meetacy.backend.types.serializable.optional.Optional
import app.meetacy.backend.types.serializable.users.User
import app.meetacy.backend.types.serializable.users.Username
import app.meetacy.di.global.di
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class EditUserParams(
    val token: AccessIdentity,
    val nickname: Optional<String> = Optional.Undefined,
    val username: Optional<Username?> = Optional.Undefined,
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

fun Route.editUser() = post("/edit") {
    val editUserRepository: EditUserRepository by di.getting

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
