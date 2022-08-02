@file:UseSerializers(AccessHashSerializer::class, AccessTokenSerializer::class, UserIdSerializer::class)

package app.meetacy.backend.endpoint.users

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.serialization.AccessHashSerializer
import app.meetacy.backend.types.serialization.AccessTokenSerializer
import app.meetacy.backend.types.serialization.UserIdSerializer
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

interface UserRepository {
    suspend fun getUser(
        id: UserId? = null,
        accessHash: AccessHash? = null,
        accessToken: AccessToken
    ): GetUserResult
}

sealed interface GetUserResult {
    object InvalidToken : GetUserResult
    object UserNotFound : GetUserResult
    class Success(val user: UserResponse) : GetUserResult
}

@Serializable
data class UserResponse(
    val id: UserId,
    val accessHash: AccessHash,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?
)

@Serializable
data class GetUserParams(
    val id: UserId? = null,
    val accessHash: AccessHash? = null,
    val accessToken: AccessToken
)

@Serializable
data class GetUserResponse(
    val status: Boolean,
    val result: UserResponse?,
    val errorCode: Int?,
    val errorMessage: String?
)

fun Route.getUser(provider: UserRepository) = post("/users/get") {
    val params = call.receive<GetUserParams>()

    val result = when (val result = provider.getUser(params.id, params.accessHash, params.accessToken)) {
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
