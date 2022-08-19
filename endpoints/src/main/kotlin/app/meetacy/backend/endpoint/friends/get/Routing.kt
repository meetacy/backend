package app.meetacy.backend.endpoint.friends.get

import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.AccessTokenSerializable
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

@Serializable
data class GetFriendsToken(
    val accessToken: AccessTokenSerializable
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
    suspend fun getFriends(token: GetFriendsToken): GetFriendsResult
}

sealed interface GetFriendsResult {
    object InvalidToken : GetFriendsResult
    class Success(val friends: List<User>, val subscriptions: List<User>) : GetFriendsResult
}

fun Route.getFriend(getProvider: GetFriendsRepository) = post("/get") {
    val friendToken = call.receive<GetFriendsToken>()
    when(val result = getProvider.getFriends(friendToken)) {
        is GetFriendsResult.Success -> call.respond(
            ResultOfSearching(
                status = false,
                friends = result.friends,
                subscriptions = result.subscriptions,
                errorCode = null,
                errorMessage = null
            )
        )
        is GetFriendsResult.InvalidToken -> call.respond(
            ResultOfSearching(
                status = false,
                friends = null,
                subscriptions = null,
                errorCode = 1,
                errorMessage = "Please provide a valid token"
            )
        )
    }
}
