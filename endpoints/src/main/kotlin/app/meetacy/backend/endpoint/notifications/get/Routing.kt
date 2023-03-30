package app.meetacy.backend.endpoint.notifications.get

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Notification
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
private data class RequestBody(
    val token: AccessIdentitySerializable,
    val offset: Long,
    val amount: Int
)

interface GetNotificationsRepository {
    suspend fun getNotifications(
        accessIdentity: AccessIdentity,
        offset: Long,
        amount: Int
    ): Result

    sealed interface Result {
        object InvalidIdentity : Result
        class Success(val notifications: List<Notification>) : Result
    }
}

fun Route.get(repository: GetNotificationsRepository) = post("/get") {
    val requestBody = call.receive<RequestBody>()

    when (
        val result = repository.getNotifications(
            accessIdentity = requestBody.token.type(),
            offset = requestBody.offset,
            amount = requestBody.amount

        )
    ) {

        is GetNotificationsRepository.Result.Success -> call.respondSuccess(result.notifications)

        is GetNotificationsRepository.Result.InvalidIdentity -> call.respondFailure(Failure.InvalidAccessIdentity)
    }
}
