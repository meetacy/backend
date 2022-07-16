package app.meetacy.backend.endpoint.friends.addNew

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

interface AddFriendsProvider {
    fun addFriends(newFriendParams: NewFriendParams): AddFriendsResult
}

data class NewFriendParams(
    val accessToken: String,
    val friendId: Long,
    val friendAccessHash: String
)


data class DataForResult(
    val status: Boolean = false,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

sealed interface AddFriendsResult {
    object Success : AddFriendsResult
    object InvalidToken : AddFriendsResult
    object FriendNotFound : AddFriendsResult
}

fun Route.addFriend(provider: AddFriendsProvider) = post("/add") {
    val params = call.receive<NewFriendParams>()
    val result = when(provider.addFriends(params)) {
        AddFriendsResult.FriendNotFound -> DataForResult(
            status = false,
            errorCode = 2,
            errorMessage = "Friend was not found"
        )
        AddFriendsResult.InvalidToken -> DataForResult(
            status = false,
            errorCode = 1,
            errorMessage = "Please provide a valid token"
        )
        AddFriendsResult.Success -> DataForResult(
            status = true
        )
    }
    call.respond(result)
}