@file:UseSerializers(AccessTokenSerializer::class, NotificationIdSerializer::class)

package app.meetacy.backend.endpoint.notifications.read

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.domain.NotificationId
import app.meetacy.backend.serialization.AccessTokenSerializer
import app.meetacy.backend.serialization.NotificationIdSerializer
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
private data class RequestBody(
    val accessToken: AccessToken,
    val lastNotificationId: NotificationId
)

@Serializable
private data class ResponseBody(
    val status: Boolean,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

interface ReadNotificationsRepository {
    suspend fun read(accessToken: AccessToken, lastNotificationId: NotificationId): Result

    sealed interface Result {
        object TokenInvalid : Result
        object LastNotificationIdInvalid : Result
        object Success : Result
    }
}

fun Route.read(repository: ReadNotificationsRepository) = post("/read") {
    val requestBody = call.receive<RequestBody>()
    val result = when (repository.read(requestBody.accessToken, requestBody.lastNotificationId)) {
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
