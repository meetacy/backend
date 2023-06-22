package app.meetacy.backend.endpoint.notifications.get

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.notification.Notification
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.amount.AmountSerializable
import app.meetacy.backend.types.serialization.paging.PagingIdSerializable
import app.meetacy.backend.types.serialization.paging.serializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
private data class RequestBody(
    val token: AccessIdentitySerializable,
    val pagingId: PagingIdSerializable? = null,
    val amount: AmountSerializable
)

interface GetNotificationsRepository {
    suspend fun getNotifications(
        accessIdentity: AccessIdentity,
        pagingId: PagingId?,
        amount: Amount
    ): Result

    sealed interface Result {
        object InvalidIdentity : Result
        class Success(val notifications: PagingResult<Notification>) : Result
    }
}

fun Route.get(repository: GetNotificationsRepository) = post("/get") {
    val requestBody = call.receive<RequestBody>()

    when (
        val result = repository.getNotifications(
            accessIdentity = requestBody.token.type(),
            pagingId = requestBody.pagingId?.type(),
            amount = requestBody.amount.type()
        )
    ) {

        is GetNotificationsRepository.Result.Success -> call.respondSuccess(result.notifications.serializable())
        is GetNotificationsRepository.Result.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
