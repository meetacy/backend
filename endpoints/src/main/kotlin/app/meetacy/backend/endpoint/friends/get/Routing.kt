package app.meetacy.backend.endpoint.friends.get

import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GetFriendsToken(
    val accessIdentity: AccessIdentitySerializable
)

interface GetFriendsRepository {
    suspend fun getFriends(token: GetFriendsToken): GetFriendsResult
}

sealed interface GetFriendsResult {
    object InvalidToken : GetFriendsResult

    @Serializable
    class Success(val friends: List<User>, val subscriptions: List<User>) : GetFriendsResult
}

fun Route.getFriend(getProvider: GetFriendsRepository) = post("/get") {
    val friendToken = call.receive<GetFriendsToken>()
    when (val result = getProvider.getFriends(friendToken)) {
        is GetFriendsResult.Success -> call.respondSuccess(
            (GetFriendsResult.Success(result.friends, result.subscriptions))
        )

        is GetFriendsResult.InvalidToken -> call.respondFailure(
            1, "Please provide a valid token"
        )
    }
}
