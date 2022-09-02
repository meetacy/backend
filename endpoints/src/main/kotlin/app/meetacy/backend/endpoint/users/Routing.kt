package app.meetacy.backend.endpoint.users

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

sealed interface GetUserResult {
    object InvalidToken : GetUserResult
    object UserNotFound : GetUserResult
    class Success(val user: User) : GetUserResult
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

fun Route.getUser(provider: UserRepository) = post("/users/get") {
    val params = call.receive<GetUserParams>()

    val result = when (
        val result = provider.getUser(
            identity = params.identity?.type(),
            accessIdentity = params.accessIdentity.type()
        )
    ) {
        is GetUserResult.Success -> GetUserResponse(
            status = true,
            result = result.user,
            errorCode = null,
            errorMessage = null
        )
        is GetUserResult.InvalidToken -> GetUserResponse(
            status = false,
            result = null,
            errorCode = 1,
            errorMessage = "Please provide a valid token"
        )
        is GetUserResult.UserNotFound -> GetUserResponse(
            status = false,
            result = null,
            errorCode = 2,
            errorMessage = "FullUser not found"
        )
    }

    call.respond(result)
}
