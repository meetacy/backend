package app.meetacy.backend.endpoint.friends.delete

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.user.UserIdentitySerializable
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
    object InvalidIdentity : DeleteFriendResult
    object FriendNotFound : DeleteFriendResult
}

fun Route.deleteFriend(provider: DeleteFriendRepository) = post("/delete") {
    val params = call.receive<DeleteFriendParams>()
    when (provider.deleteFriend(params)) {

        DeleteFriendResult.FriendNotFound -> call.respondFailure(Failure.FriendNotFound)
        DeleteFriendResult.InvalidIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
        DeleteFriendResult.Success -> call.respondSuccess()
    }
}