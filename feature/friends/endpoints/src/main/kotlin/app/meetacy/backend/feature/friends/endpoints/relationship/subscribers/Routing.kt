package app.meetacy.backend.feature.friends.endpoints.relationship.subscribers

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.core.endpoints.amount
import app.meetacy.backend.core.endpoints.pagingIdOrNull
import app.meetacy.backend.core.endpoints.userIdOrNull
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.PagingResult
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.users.UserDetails
import app.meetacy.backend.types.serializable.users.UserId
import io.ktor.server.application.*
import io.ktor.server.routing.*

interface GetSubscribersRepository {
    suspend fun getSubscribers(identifier: Identifier): GetSubscribersResult

    sealed interface Identifier {
        val accessIdentity: AccessIdentity
        val amount: Amount
        val pagingId: PagingId?

        data class Self(
            override val accessIdentity: AccessIdentity,
            override val amount: Amount,
            override val pagingId: PagingId?
        ) : Identifier
        data class ByUserId(
            val userId: UserId,
            override val accessIdentity: AccessIdentity,
            override val amount: Amount,
            override val pagingId: PagingId?,
        ) : Identifier
    }
}

sealed interface GetSubscribersResult {
    data object InvalidIdentity : GetSubscribersResult
    data object UserNotFound : GetSubscribersResult
    class Success(val paging: PagingResult<UserDetails>) : GetSubscribersResult
}

fun Route.getSubscribers(provider: GetSubscribersRepository) = get("/subscribers") {
    val token = call.accessIdentity()
    val id = call.parameters.userIdOrNull(name = "id")
    val amount = call.parameters.amount()
    val pagingId = call.parameters.pagingIdOrNull()

    val identifier = when {
        id != null -> GetSubscribersRepository.Identifier.ByUserId(id, token, amount, pagingId)
        else -> GetSubscribersRepository.Identifier.Self(token, amount, pagingId)
    }

    when (val result = provider.getSubscribers(identifier)) {
        is GetSubscribersResult.Success -> call.respondSuccess(result.paging)
        GetSubscribersResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        GetSubscribersResult.UserNotFound -> call.respondFailure(Failure.UserNotFound)
    }
}

