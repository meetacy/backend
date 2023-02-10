package app.meetacy.backend.endpoint.friends.delete

import app.meetacy.backend.endpoint.ktor.respondEmptySuccess
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.UserIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

interface DeleteFriendRepository {
    suspend fun deleteFriend(deleteFriendParams: DeleteFriendParams): DeleteFriendResult
}

@Serializable
data class DeleteFriendParams(
    val accessIdentity: AccessIdentitySerializable,
    val friendIdentity: UserIdentitySerializable
)

sealed interface DeleteFriendResult {
    object Success : DeleteFriendResult
    object InvalidToken : DeleteFriendResult
    object FriendNotFound : DeleteFriendResult
}

fun Route.deleteFriend(provider: DeleteFriendRepository) = post("/delete") {
    val params = call.receive<DeleteFriendParams>()
    when(provider.deleteFriend(params)) {
        DeleteFriendResult.FriendNotFound -> call.respondFailure(
            2, "Friend was not found"
        )
        DeleteFriendResult.InvalidToken -> call.respondFailure(
            1, "Please provide a valid token"
        )
        DeleteFriendResult.Success -> call.respondEmptySuccess()
    }
}