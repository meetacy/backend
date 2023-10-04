package app.meetacy.backend.feature.notifications.endpoints.read

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.notification.NotificationId

@Serializable
private data class RequestBody(
    val lastNotificationId: NotificationId
)

interface ReadNotificationsRepository {
    suspend fun read(accessIdentity: AccessIdentity, lastNotificationId: NotificationId): Result

    sealed interface Result {
        data object InvalidIdentity : Result
        data object LastNotificationIdInvalid : Result
        data object Success : Result
    }
}

fun Route.read(repository: ReadNotificationsRepository) = post("/read") {
    val requestBody = call.receive<RequestBody>()
    val token = call.accessIdentity()

    when (repository.read(token, requestBody.lastNotificationId)) {

        ReadNotificationsRepository.Result.Success -> call.respondSuccess()

        ReadNotificationsRepository.Result.LastNotificationIdInvalid -> call.respondFailure(Failure.LastNotificationIdInvalid)
        ReadNotificationsRepository.Result.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
