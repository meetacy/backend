package app.meetacy.backend.feature.friends.endpoints.add

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.users.UserId as UserIdentitySerializable

interface AddFriendRepository {
    suspend fun addFriend(token: AccessIdentity, friendId: UserIdentitySerializable): AddFriendResult
}

@Serializable
data class AddFriendParam(
    val friendId: UserIdentitySerializable
)

sealed interface AddFriendResult {
    data object Success : AddFriendResult
    data object InvalidIdentity : AddFriendResult
    data object FriendNotFound : AddFriendResult
    data object FriendAlreadyAdded : AddFriendResult
}

fun Route.addFriend(provider: AddFriendRepository) = post("/add") {
    val param = call.receive<AddFriendParam>()
    val token = call.accessIdentity()
    when (provider.addFriend(token, param.friendId)) {
        AddFriendResult.Success -> call.respondSuccess()
        AddFriendResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        AddFriendResult.FriendNotFound -> call.respondFailure(Failure.FriendNotFound)
        AddFriendResult.FriendAlreadyAdded -> call.respondFailure(Failure.FriendAlreadyAdded)
    }
}
