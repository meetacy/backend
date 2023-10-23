package app.meetacy.backend.feature.friends.endpoints.list

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.PagingResult
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.users.User
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ListFriendsBody(
    val amount: Amount,
    val pagingId: PagingId? = null
)

interface ListFriendsRepository {
    suspend fun getFriends(token: AccessIdentity, amount: Amount, pagingId: PagingId?): ListFriendsResult
}

sealed interface ListFriendsResult {
    data object InvalidIdentity : ListFriendsResult

    class Success(val paging: PagingResult<User>) : ListFriendsResult
}

fun Route.listFriends(getProvider: ListFriendsRepository) = post("/list") {
    val params = call.receive<ListFriendsBody>()
    val token = call.accessIdentity()
    when (val result = getProvider.getFriends(token, params.amount, params.pagingId)) {
        is ListFriendsResult.Success -> call.respondSuccess(result.paging)
        is ListFriendsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
