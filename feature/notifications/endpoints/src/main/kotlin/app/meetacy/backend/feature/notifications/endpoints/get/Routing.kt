package app.meetacy.backend.feature.notifications.endpoints.get

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.notification.Notification
import app.meetacy.backend.types.paging.serializable.PagingResult
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.amount.Amount
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
private data class RequestBody(
    val pagingId: PagingId? = null,
    val amount: Amount
)

interface ListNotificationsRepository {
    suspend fun getNotifications(
        token: AccessIdentity,
        pagingId: PagingId?,
        amount: Amount
    ): Result

    sealed interface Result {
        data object InvalidIdentity : Result
        class Success(val notifications: PagingResult<Notification>) : Result
    }
}

fun Route.list(repository: ListNotificationsRepository) = post("/list") {
    val requestBody = call.receive<RequestBody>()
    val token = call.accessIdentity()
    when (
        val result = repository.getNotifications(
            token = token,
            pagingId = requestBody.pagingId,
            amount = requestBody.amount
        )
    ) {

        is ListNotificationsRepository.Result.Success -> call.respondSuccess(result.notifications)
        is ListNotificationsRepository.Result.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
