package app.meetacy.backend.endpoint.notifications.read

import app.meetacy.backend.endpoint.ktor.respondEmptySuccess
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.NotificationId
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.NotificationIdSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
private data class RequestBody(
    val accessIdentity: AccessIdentitySerializable,
    val lastNotificationId: NotificationIdSerializable
)

interface ReadNotificationsRepository {
    suspend fun read(accessIdentity: AccessIdentity, lastNotificationId: NotificationId): Result

    sealed interface Result {
        object TokenInvalid : Result
        object LastNotificationIdInvalid : Result
        object Success : Result
    }
}

fun Route.read(repository: ReadNotificationsRepository) = post("/read") {
    val requestBody = call.receive<RequestBody>()
    when (repository.read(requestBody.accessIdentity.type(), requestBody.lastNotificationId.type())) {
        ReadNotificationsRepository.Result.Success -> call.respondEmptySuccess()
        ReadNotificationsRepository.Result.LastNotificationIdInvalid -> call.respondFailure(
            1, "Please provide a valid notification id"
        )
        ReadNotificationsRepository.Result.TokenInvalid -> call.respondFailure(
            1, "Please provide a valid token"
        )
    }
}
