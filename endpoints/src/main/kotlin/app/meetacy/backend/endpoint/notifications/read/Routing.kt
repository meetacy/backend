package app.meetacy.backend.endpoint.notifications.read

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.NotificationId
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.NotificationIdSerializable
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

@Serializable
private data class RequestBody(
    val accessIdentity: AccessIdentitySerializable,
    val lastNotificationId: NotificationIdSerializable
)

@Serializable
private data class ResponseBody(
    val status: Boolean,
    val errorCode: Int? = null,
    val errorMessage: String? = null
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
    val result = when (repository.read(requestBody.accessIdentity.type(), requestBody.lastNotificationId.type())) {
        ReadNotificationsRepository.Result.Success -> ResponseBody(
            status = true
        )
        ReadNotificationsRepository.Result.LastNotificationIdInvalid ->
            ResponseBody(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid notification id"
            )
        ReadNotificationsRepository.Result.TokenInvalid ->
            ResponseBody(
                status = false,
                errorCode = 1,
                errorMessage = "Please provide a valid token"
            )
    }
    call.respond(result)
}
