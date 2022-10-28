package app.meetacy.backend.endpoint.users.get

import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.UserIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
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

@Serializable
data class GetUserResponse(
    val status: Boolean,
    val result: User?,
    val errorCode: Int?,
    val errorMessage: String?
)

sealed interface GetUserResult {
    object InvalidToken : GetUserResult
    object UserNotFound : GetUserResult
    class Success(val user: User) : GetUserResult
}

fun Route.getUser(provider: UserRepository) = post("/get") {
    val params = call.receive<GetUserParams>()

    when (val result = with(params) { provider.getUser(identity?.type(), accessIdentity.type()) }) {
        GetUserResult.UserNotFound -> call.respond(
            GetUserResponse(
                status = false,
                result = null,
                errorCode = 2,
                errorMessage = "FullUser not found"
            )
        )
        is GetUserResult.Success -> call.respond(
            GetUserResponse(
                status = true,
                result = result.user,
                errorCode = null,
                errorMessage = null
            )
        )
        GetUserResult.InvalidToken -> call.respond(
            GetUserResponse(
                status = false,
                result = null,
                errorCode = 1,
                errorMessage = "Please provide a valid token"
            )
        )
    }
}
