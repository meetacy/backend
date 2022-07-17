package app.meetacy.backend.endpoint.users

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

interface UserProvider{
    fun getUser(getUserParams: GetUserParams): GetUserResult
}

sealed interface GetUserResult {
    object InvalidToken : GetUserResult
    object UserNotFound : GetUserResult
    class Success(val user: UserResponse) : GetUserResult
}

@Serializable
data class UserResponse(
    val id: Long,
    val accessHash: String,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?
)

@Serializable
data class GetUserParams(
    val id: Long? = null,
    val accessHash: String? = null,
    val accessToken: String
)

@Serializable
data class GetUserResponse(
    val status: Boolean,
    val result: UserResponse?,
    val errorCode: Int?,
    val errorMessage: String?
)

fun Route.getUser(provider: UserProvider) = post("/users/get") {
    val params = call.receive<GetUserParams>()

    val result = when (val result = provider.getUser(params)) {
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
            errorMessage = "User not found"
        )
    }

    call.respond(result)
}
