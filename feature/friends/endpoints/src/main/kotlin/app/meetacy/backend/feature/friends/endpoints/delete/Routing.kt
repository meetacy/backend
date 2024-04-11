package app.meetacy.backend.feature.friends.endpoints.delete

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.core.endpoints.friendId
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.users.UserId as UserIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

interface DeleteFriendRepository {
    suspend fun deleteFriend(token: AccessIdentity, friendId: UserIdentitySerializable): DeleteFriendResult
}

sealed interface DeleteFriendResult {
    data object Success : DeleteFriendResult
    data object InvalidIdentity : DeleteFriendResult
    data object FriendNotFound : DeleteFriendResult
}

fun Route.deleteFriend(provider: DeleteFriendRepository) = delete("/delete") {
    val param = call.parameters.friendId()
    val token = call.accessIdentity()

    when (provider.deleteFriend(token, param)) {
        DeleteFriendResult.FriendNotFound -> call.respondFailure(Failure.FriendNotFound)
        DeleteFriendResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        DeleteFriendResult.Success -> call.respondSuccess()
    }
}
