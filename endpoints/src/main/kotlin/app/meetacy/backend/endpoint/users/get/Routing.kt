package app.meetacy.backend.endpoint.users.get

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.user.UserIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

interface UserRepository {
    suspend fun getUser(params: GetUserParams): GetUserResult
}

@Serializable
data class GetUserParams(
    val id: UserIdentitySerializable? = null,
    val token: AccessIdentitySerializable
)

sealed interface GetUserResult {
    object InvalidIdentity : GetUserResult
    object UserNotFound : GetUserResult
    class Success(val user: User) : GetUserResult
}

fun Route.getUser(provider: UserRepository) = get("/get") {
    val params = call.receive<GetUserParams>()

    when (val result = provider.getUser(params)) {
        is GetUserResult.Success -> call.respondSuccess(result.user)
        GetUserResult.UserNotFound -> call.respondFailure(Failure.UserNotFound)
        GetUserResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
