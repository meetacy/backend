@file:UseSerializers(AccessTokenSerializer::class)

package app.meetacy.backend.endpoint.notifications.get

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.endpoint.models.Notification
import app.meetacy.backend.serialization.AccessTokenSerializer
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
private data class RequestBody(
    val accessToken: AccessToken,
    val offset: Long,
    val amount: Int
)

@Serializable
private data class ResponseBody(
    val status: Boolean,
    val result: List<Notification>?,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

interface GetNotificationsRepository {
    fun getNotifications(accessToken: AccessToken): Result

    sealed interface Result {
        object TokenInvalid : Result
        class Success(val notifications: List<Notification>) : Result
    }
}

fun Route.get(repository: GetNotificationsRepository) = post("/get") {
    val requestBody = call.receive<RequestBody>()
    val result = when (val result = repository.getNotifications(requestBody.accessToken)) {
        is GetNotificationsRepository.Result.Success -> ResponseBody(
            status = true,
            result = result.notifications
        )
        is GetNotificationsRepository.Result.TokenInvalid -> ResponseBody(
            status = false,
            result = null,
            errorCode = 1,
            errorMessage = "Please provide a valid token"
        )
    }
    call.respond(result)
}
