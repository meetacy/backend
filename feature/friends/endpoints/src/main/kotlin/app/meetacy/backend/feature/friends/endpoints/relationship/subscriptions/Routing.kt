package app.meetacy.backend.feature.friends.endpoints.relationship.subscriptions

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
import app.meetacy.backend.types.serializable.users.User
import app.meetacy.backend.types.serializable.users.UserId
import io.ktor.server.application.*
import io.ktor.server.routing.*

interface GetSubscriptionsRepository {
    suspend fun getSubscriptions(identifier: Identifier): GetSubscriptionsResult

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

sealed interface GetSubscriptionsResult {
    data object InvalidIdentity : GetSubscriptionsResult
    data object UserNotFound : GetSubscriptionsResult
    class Success(val paging: PagingResult<User>) : GetSubscriptionsResult
}

fun Route.getSubscriptions(provider: GetSubscriptionsRepository) = post("/subscriptions") {
    val token = call.accessIdentity()
    val id = call.parameters.userIdOrNull(name = "id")
    val amount = call.parameters.amount()
    val pagingId = call.parameters.pagingIdOrNull()

    val identifier = when {
        id != null -> GetSubscriptionsRepository.Identifier.ByUserId(id, token, amount, pagingId)
        else -> GetSubscriptionsRepository.Identifier.Self(token, amount, pagingId)
    }

    when (val result = provider.getSubscriptions(identifier)) {
        is GetSubscriptionsResult.Success -> call.respondSuccess(result.paging)
        GetSubscriptionsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        GetSubscriptionsResult.UserNotFound -> call.respondFailure(Failure.UserNotFound)
    }
}

