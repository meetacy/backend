package app.meetacy.backend.endpoint.users

import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.serialization.AccessHashSerializable
import app.meetacy.backend.types.serialization.AccessTokenSerializable
import app.meetacy.backend.types.serialization.UserIdSerializable
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
    val id: UserIdSerializable,
    val accessHash: AccessHashSerializable,
    val nickname: String,
    val email: String?,
    val emailVerified: Boolean?
)

@Serializable
data class GetUserParams(
    val id: UserIdSerializable? = null,
    val accessHash: AccessHashSerializable? = null,
    val accessToken: AccessTokenSerializable
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

    val result = when (
        val result = provider.getUser(
            id = params.id?.type(),
            accessHash = params.accessHash?.type(),
            accessToken = params.accessToken.type()
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
