package app.meetacy.backend.endpoint.auth.email.confirm

import app.meetacy.backend.endpoint.ktor.respondEmptySuccess
import app.meetacy.backend.endpoint.ktor.respondFailure
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmParams(
    val email: String,
    val confirmHash: String
)

sealed interface ConfirmHashResult {
    object LinkExpired : ConfirmHashResult
    object LinkInvalid : ConfirmHashResult
    object LinkMaxAttemptsReached : ConfirmHashResult
    object Success : ConfirmHashResult
}

interface ConfirmEmailRepository {
    suspend fun checkConfirmHash(email: String, confirmHash: String): ConfirmHashResult
}

fun Route.confirmEmail(storage: ConfirmEmailRepository) = post("/confirm") {
    val parameters = call.receive<ConfirmParams>()

    when (storage.checkConfirmHash(parameters.email, parameters.confirmHash)) {
        ConfirmHashResult.LinkExpired -> call.respondFailure(
            1, "This link was expired. Please consider to create a new one."
        )
        ConfirmHashResult.LinkInvalid -> call.respondFailure(
            2, "This link is invalid. Please consider to create a new one."
        )
        ConfirmHashResult.LinkMaxAttemptsReached -> call.respondFailure(
            3, "You have reached max attempts for today. Please try again later."
        )
        ConfirmHashResult.Success -> call.respondEmptySuccess()
    }
}
