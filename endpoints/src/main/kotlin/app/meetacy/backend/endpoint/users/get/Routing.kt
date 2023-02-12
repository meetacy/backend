package app.meetacy.backend.endpoint.users.get

import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.UserIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

interface UserRepository {
    suspend fun getUser(
        identity: UserIdentity? = null,
        accessIdentity: AccessIdentity
    ): GetUserResult
}

@Serializable
data class GetUserParams(
    val identity: UserIdentitySerializable? = null,
    val accessIdentity: AccessIdentitySerializable
)

sealed interface GetUserResult {
    object InvalidToken : GetUserResult
    object UserNotFound : GetUserResult
    class Success(val user: User) : GetUserResult
}

fun Route.getUser(provider: UserRepository) = post("/get") {
    val params = call.receive<GetUserParams>()

    when (val result = with(params) { provider.getUser(identity?.type(), accessIdentity.type()) }) {
        is GetUserResult.Success -> call.respondSuccess(result.user)
        GetUserResult.UserNotFound -> call.respondFailure(
            2, "FullUser not found"
        )

        GetUserResult.InvalidToken -> call.respondFailure(
            1, "Please provide a valid token"
        )
    }
}
