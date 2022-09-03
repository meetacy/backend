package app.meetacy.backend.endpoint.friends.add

import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.UserIdentitySerializable
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

interface AddFriendRepository {
    suspend fun addFriend(addFriendParams: AddFriendParams): AddFriendResult
}

@Serializable
data class AddFriendParams(
    val accessIdentity: AccessIdentitySerializable,
    val friendIdentity: UserIdentitySerializable
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
    object FriendAlreadyAdded : AddFriendResult
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
        AddFriendResult.FriendAlreadyAdded -> AddFriendResponse(
            status = false,
            errorCode = 3,
            errorMessage = "Friend already added"
        )
    }
    call.respond(result)
}