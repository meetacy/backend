package app.meetacy.backend.endpoint.auth.email.confirm

import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmParams(
    val email: String,
    val confirmHash: String
)

@Serializable
private data class ConfirmResponse(
    val status: Boolean,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

sealed interface ConfirmHashResult {
    object LinkExpired : ConfirmHashResult
    object LinkInvalid : ConfirmHashResult
    object LinkMaxAttemptsReached : ConfirmHashResult
    object Success : ConfirmHashResult
}

interface ConfirmStorage {
    suspend fun checkConfirmHash(email: String, confirmHash: String): ConfirmHashResult
}

fun Route.confirmEmail(storage: ConfirmStorage) = post("/confirm") {
    val parameters = call.receive<ConfirmParams>()

    val response = when (storage.checkConfirmHash(parameters.email, parameters.confirmHash)) {
        ConfirmHashResult.LinkExpired -> ConfirmResponse(
            status = false,
            errorCode = 1,
            errorMessage = "This link was expired. Please consider to create a new one."
        )
        ConfirmHashResult.LinkInvalid -> ConfirmResponse(
            status = false,
            errorCode = 2,
            errorMessage = "This link is invalid. Please consider to create a new one."
        )
        ConfirmHashResult.LinkMaxAttemptsReached -> ConfirmResponse(
            status = false,
            errorCode = 3,
            errorMessage = "You have reached max attempts for today. Please try again later."
        )
        ConfirmHashResult.Success -> ConfirmResponse(status = true)
    }

    call.respond(response)
}
