package app.meetacy.backend.endpoint.notifications.read

import app.meetacy.backend.endpoint.ktor.ResponseCode
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
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
        object InvalidIdentity : Result
        object LastNotificationIdInvalid : Result
        object Success : Result
    }
}

fun Route.read(repository: ReadNotificationsRepository) = post("/read") {
    val requestBody = call.receive<RequestBody>()
    when (repository.read(requestBody.accessIdentity.type(), requestBody.lastNotificationId.type())) {

        ReadNotificationsRepository.Result.Success -> call.respondSuccess()

        ReadNotificationsRepository.Result.LastNotificationIdInvalid -> call.respondFailure(ResponseCode.LastNotificationIdInvalid)
        ReadNotificationsRepository.Result.InvalidIdentity -> call.respondFailure(ResponseCode.InvalidAccessIdentity)
    }
}
