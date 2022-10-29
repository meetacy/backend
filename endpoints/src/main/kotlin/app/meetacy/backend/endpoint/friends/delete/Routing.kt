package app.meetacy.backend.endpoint.friends.delete

import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.UserIdentitySerializable
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

interface DeleteFriendRepository {
    suspend fun deleteFriend(deleteFriendParams: DeleteFriendParams): DeleteFriendResult
}

@Serializable
data class DeleteFriendParams(
    val accessIdentity: AccessIdentitySerializable,
    val friendIdentity: UserIdentitySerializable
)

@Serializable
data class DeleteFriendResponse(
    val status: Boolean = false,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

sealed interface DeleteFriendResult {
    object Success : DeleteFriendResult
    object InvalidToken : DeleteFriendResult
    object FriendNotFound : DeleteFriendResult
}

fun Route.deleteFriend(provider: DeleteFriendRepository) = post("/delete") {
    val params = call.receive<DeleteFriendParams>()
    val result = when(provider.deleteFriend(params)) {
        DeleteFriendResult.FriendNotFound -> DeleteFriendResponse(
            status = false,
            errorCode = 2,
            errorMessage = "Friend was not found"
        )
        DeleteFriendResult.InvalidToken -> DeleteFriendResponse(
            status = false,
            errorCode = 1,
            errorMessage = "Please provide a valid token"
        )
        DeleteFriendResult.Success -> DeleteFriendResponse(
            status = true
        )
    }
    call.respond(result)
}
