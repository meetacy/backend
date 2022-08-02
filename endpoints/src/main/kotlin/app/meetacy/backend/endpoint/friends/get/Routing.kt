package app.meetacy.backend.endpoint.friends.get

import app.meetacy.backend.endpoint.types.User
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GetFriendsToken(
    val accessToken: String
)

@Serializable
data class ResultOfSearching(
    val status: Boolean = false,
    val friends: List<User>? = null,
    val subscriptions: List<User>? = null,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

interface GetFriendsRepository {
    fun getFriends(token: GetFriendsToken): GetFriendsResult
}

sealed interface GetFriendsResult {
    object InvalidToken : GetFriendsResult
    class Success(val friends: List<User>, val subscriptions: List<User>) : GetFriendsResult
}

fun Route.getFriend(getProvider: GetFriendsRepository) = post("/get") {
    val friendToken = call.receive<GetFriendsToken>()
    val result = getProvider.getFriends(friendToken)
    when(result) {
        is GetFriendsResult.Success -> call.respond(ResultOfSearching(
            status = false,
            friends = result.friends,
            subscriptions = result.subscriptions,
            errorCode = null,
            errorMessage = null
            )
        )
        is GetFriendsResult.InvalidToken -> call.respond(ResultOfSearching(
            status = false,
            friends = null,
            subscriptions = null,
            errorCode = 1,
            errorMessage = "Please provide a valid token"
            )
        )
    }
}