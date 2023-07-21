package app.meetacy.backend.endpoint.notifications.read

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.notification.NotificationId
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.notification.NotificationIdSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
private data class RequestBody(
    val token: AccessIdentitySerializable,
    val lastNotificationId: NotificationIdSerializable
)

interface ReadNotificationsRepository {
    suspend fun read(accessIdentity: AccessIdentity, lastNotificationId: NotificationId): Result

    sealed interface Result {
        object InvalidIdentity : Result
        object LastNotificationIdInvalid : Result
        object Success : Result
    }
}

fun Route.read(repository: ReadNotificationsRepository) = post("/read") {
    val requestBody = call.receive<RequestBody>()
    when (repository.read(requestBody.token.type(), requestBody.lastNotificationId.type())) {

        ReadNotificationsRepository.Result.Success -> call.respondSuccess()

        ReadNotificationsRepository.Result.LastNotificationIdInvalid -> call.respondFailure(Failure.LastNotificationIdInvalid)
        ReadNotificationsRepository.Result.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
