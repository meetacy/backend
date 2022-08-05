package app.meetacy.backend.endpoint.notifications.get

import app.meetacy.backend.endpoint.types.Notification
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.serialization.AccessTokenSerializable
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
private data class RequestBody(
    val accessToken: AccessTokenSerializable,
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
    suspend fun getNotifications(
        accessToken: AccessToken,
        offset: Long,
        amount: Int
    ): Result

    sealed interface Result {
        object TokenInvalid : Result
        class Success(val notifications: List<Notification>) : Result
    }
}

fun Route.get(repository: GetNotificationsRepository) = post("/get") {
    val requestBody = call.receive<RequestBody>()

    val result = when (
        val result = repository.getNotifications(
            accessToken = requestBody.accessToken.type(),
            offset = requestBody.offset,
            amount = requestBody.amount
        )
    ) {
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
