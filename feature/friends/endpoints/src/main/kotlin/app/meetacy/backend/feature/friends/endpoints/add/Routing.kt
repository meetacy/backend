package app.meetacy.backend.feature.friends.endpoints.add

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.users.UserIdentity as UserIdentitySerializable

interface AddFriendRepository {
    suspend fun addFriend(addFriendParams: AddFriendParams): AddFriendResult
}

@Serializable
data class AddFriendParams(
    val token: AccessIdentity,
    val friendId: UserIdentitySerializable
)

sealed interface AddFriendResult {
    data object Success : AddFriendResult
    data object InvalidIdentity : AddFriendResult
    data object FriendNotFound : AddFriendResult
    data object FriendAlreadyAdded : AddFriendResult
}

fun Route.addFriend(provider: AddFriendRepository) = post("/add") {
    val params = call.receive<AddFriendParams>()
    when (provider.addFriend(params)) {
        AddFriendResult.Success -> call.respondSuccess()
        AddFriendResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        AddFriendResult.FriendNotFound -> call.respondFailure(Failure.FriendNotFound)
        AddFriendResult.FriendAlreadyAdded -> call.respondFailure(Failure.FriendAlreadyAdded)
    }
}
