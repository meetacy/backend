package app.meetacy.backend.endpoint.friends.add

import app.meetacy.backend.types.serialization.AccessHashSerializable
import app.meetacy.backend.types.serialization.AccessTokenSerializable
import app.meetacy.backend.types.serialization.UserIdSerializable
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

interface AddFriendRepository {
    fun addFriend(addFriendParams: AddFriendParams): AddFriendResult
}

@Serializable
data class AddFriendParams(
    val accessToken: AccessTokenSerializable,
    val friendId: UserIdSerializable,
    val friendAccessHash: AccessHashSerializable
)

@Serializable
data class AddFriendResponse(
    val status: Boolean = false,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

sealed interface AddFriendResult {
    object Success : AddFriendResult
    object InvalidToken : AddFriendResult
    object FriendNotFound : AddFriendResult
}

fun Route.addFriend(provider: AddFriendRepository) = post("/add") {
    val params = call.receive<AddFriendParams>()
    val result = when(provider.addFriend(params)) {
        AddFriendResult.FriendNotFound -> AddFriendResponse(
            status = false,
            errorCode = 2,
            errorMessage = "Friend was not found"
        )
        AddFriendResult.InvalidToken -> AddFriendResponse(
            status = false,
            errorCode = 1,
            errorMessage = "Please provide a valid token"
        )
        AddFriendResult.Success -> AddFriendResponse(
            status = true
        )
    }
    call.respond(result)
}