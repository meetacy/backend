package app.meetacy.backend.feature.email.endpoints.confirm

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.lang.IllegalStateException

@Serializable
data class ConfirmParams(
    val email: String,
    val confirmHash: String
)

sealed interface ConfirmHashResult {
    data object LinkExpired : ConfirmHashResult
    data object LinkInvalid : ConfirmHashResult
    data object LinkMaxAttemptsReached : ConfirmHashResult
    data object Success : ConfirmHashResult
}

interface ConfirmEmailRepository {
    suspend fun checkConfirmHash(email: String, confirmHash: String): ConfirmHashResult
}

fun Route.confirmEmail(repository: ConfirmEmailRepository) = post("/confirm") {
    val parameters = call.receive<ConfirmParams>()

    when (repository.checkConfirmHash(parameters.email, parameters.confirmHash)) {
        ConfirmHashResult.LinkExpired -> call.respondFailure(Failure.ExpiredLink)
        ConfirmHashResult.LinkInvalid -> call.respondFailure(Failure.InvalidLink)
        ConfirmHashResult.LinkMaxAttemptsReached -> call.respondFailure(Failure.LinkMaxAttemptsReached)
        ConfirmHashResult.Success -> call.respondSuccess()
    }
}
