package app.meetacy.backend.endpoint.users

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

interface UserProvider{
    fun getUser(getUserParams: GetUserParams): UserResponse?
}

@Serializable
data class GetUserParams(
    val id: Int?,
    val accessHash: Int?,
    val accessToken: String
)

@Serializable
data class GetUserResponse(
    val result: UserResponse?
)

@Serializable
data class UserResponse(
    val id: Int,
    val nickname: String,
    val email: String?
)

fun Route.getUser(provider: UserProvider) = post("/users") {
    val params = call.receive<GetUserParams>()
    val result = provider.getUser(params)
    call.respond(GetUserResponse(result))
}