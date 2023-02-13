package app.meetacy.backend.endpoint.friends.add

import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.UserIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

interface AddFriendRepository {
    suspend fun addFriend(addFriendParams: AddFriendParams): AddFriendResult
}

@Serializable
data class AddFriendParams(
    val accessIdentity: AccessIdentitySerializable,
    val friendIdentity: UserIdentitySerializable
)

sealed interface AddFriendResult {
    object Success : AddFriendResult
    object InvalidIdentity : AddFriendResult
    object FriendNotFound : AddFriendResult
    object FriendAlreadyAdded : AddFriendResult
}

fun Route.addFriend(provider: AddFriendRepository) = post("/add") {
    val params = call.receive<AddFriendParams>()
    when (provider.addFriend(params)) {
        AddFriendResult.Success -> call.respondSuccess()
        AddFriendResult.InvalidIdentity -> call.respondFailure(1, "Please provide a valid accessIdentity")
        AddFriendResult.FriendNotFound -> call.respondFailure(2, "Friend was not found")
        AddFriendResult.FriendAlreadyAdded -> call.respondFailure(3, "Friend already added")
    }
}
