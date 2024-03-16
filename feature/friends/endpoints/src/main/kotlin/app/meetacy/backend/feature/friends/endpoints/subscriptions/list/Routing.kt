package app.meetacy.backend.feature.friends.endpoints.subscriptions.list

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
import app.meetacy.backend.types.serializable.users.UserDetails
import app.meetacy.backend.types.serializable.users.UserId
import io.ktor.server.application.*
import io.ktor.server.routing.*

interface ListSubscriptionsRepository {
    suspend fun listSubscriptions(identifier: Identifier): ListSubscriptionsResult

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

sealed interface ListSubscriptionsResult {
    data object InvalidIdentity : ListSubscriptionsResult
    data object UserNotFound : ListSubscriptionsResult
    class Success(val paging: PagingResult<User>) : ListSubscriptionsResult
}

fun Route.listSubscriptions(provider: ListSubscriptionsRepository) = get("/list") {
    val token = call.accessIdentity()
    val id = call.parameters.userIdOrNull()
    val amount = call.parameters.amount()
    val pagingId = call.parameters.pagingIdOrNull()

    val identifier = when {
        id != null -> ListSubscriptionsRepository.Identifier.ByUserId(id, token, amount, pagingId)
        else -> ListSubscriptionsRepository.Identifier.Self(token, amount, pagingId)
    }

    when (val result = provider.listSubscriptions(identifier)) {
        is ListSubscriptionsResult.Success -> call.respondSuccess(result.paging)
        ListSubscriptionsResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        ListSubscriptionsResult.UserNotFound -> call.respondFailure(Failure.UserNotFound)
    }
}
