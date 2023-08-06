package app.meetacy.backend.endpoint.friends.delete

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.user.UserIdentity as UserIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

interface DeleteFriendRepository {
    suspend fun deleteFriend(deleteFriendParams: DeleteFriendParams): DeleteFriendResult
}

@Serializable
data class DeleteFriendParams(
    val token: AccessIdentity,
    val friendId: UserIdentitySerializable
)

sealed interface DeleteFriendResult {
    data object Success : DeleteFriendResult
    data object InvalidIdentity : DeleteFriendResult
    data object FriendNotFound : DeleteFriendResult
}

fun Route.deleteFriend(provider: DeleteFriendRepository) = post("/delete") {
    val params = call.receive<DeleteFriendParams>()
    when (provider.deleteFriend(params)) {

        DeleteFriendResult.FriendNotFound -> call.respondFailure(Failure.FriendNotFound)
        DeleteFriendResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        DeleteFriendResult.Success -> call.respondSuccess()
    }
}