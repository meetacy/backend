@file:UseSerializers(AccessHashSerializer::class, AccessTokenSerializer::class, UserIdSerializer::class)

package app.meetacy.backend.endpoint.friends.add

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

interface AddFriendRepository {
    fun addFriend(addFriendParams: AddFriendParams): AddFriendResult
}

@Serializable
data class AddFriendParams(
    val accessToken: AccessToken,
    val friendId: UserId,
    val friendAccessHash: AccessHash
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