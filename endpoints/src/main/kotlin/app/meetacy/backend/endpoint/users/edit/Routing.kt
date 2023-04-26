package app.meetacy.backend.endpoint.users.edit

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.OptionalSerializable
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.file.FileIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class EditUserParams(
    val token: AccessIdentitySerializable,
    val nickname: String?,
    val username: OptionalSerializable<String?> = OptionalSerializable.Undefined,
    val avatarId: OptionalSerializable<FileIdentitySerializable?> = OptionalSerializable.Undefined,
)

sealed interface EditUserResult {
    class Success(val user: User) : EditUserResult
    object InvalidAccessIdentity : EditUserResult
    object InvalidUtf8String : EditUserResult
    object NullEditParameters : EditUserResult
    object InvalidAvatarIdentity : EditUserResult
    object InvalidUsername : EditUserResult
}

interface EditUserRepository {
    suspend fun editUser(editUserParams: EditUserParams): EditUserResult
}

fun Route.editUser(editUserRepository: EditUserRepository) = post("/edit") {
    val params = call.receive<EditUserParams>()

    when (val result = editUserRepository.editUser(params)) {
        is EditUserResult.Success-> call.respondSuccess(result.user)
        EditUserResult.InvalidAccessIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
        EditUserResult.InvalidUtf8String -> call.respondFailure(Failure.InvalidTitleOrDescription)
        EditUserResult.InvalidAvatarIdentity -> call.respondFailure(Failure.InvalidFileIdentity)
        EditUserResult.NullEditParameters -> call.respondFailure(Failure.NullEditParams)
        EditUserResult.InvalidUsername -> call.respondFailure(Failure.InvalidUsername)
    }
}
