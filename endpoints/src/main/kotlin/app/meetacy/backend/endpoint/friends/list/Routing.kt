package app.meetacy.backend.endpoint.friends.list

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.amount.AmountSerializable
import app.meetacy.backend.types.serialization.paging.PagingIdSerializable
import app.meetacy.backend.types.serialization.paging.serializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ListFriendsBody(
    val token: AccessIdentitySerializable,
    val amount: AmountSerializable,
    val pagingId: PagingIdSerializable? = null
)

interface ListFriendsRepository {
    suspend fun getFriends(token: ListFriendsBody): ListFriendsResult
}

sealed interface ListFriendsResult {
    object InvalidIdentity : ListFriendsResult

    class Success(val paging: PagingResult<List<User>>) : ListFriendsResult
}

fun Route.listFriends(getProvider: ListFriendsRepository) = post("/list") {
    val friendToken = call.receive<ListFriendsBody>()
    when (val result = getProvider.getFriends(friendToken)) {
        is ListFriendsResult.Success -> call.respondSuccess(result.paging.serializable())
        is ListFriendsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
