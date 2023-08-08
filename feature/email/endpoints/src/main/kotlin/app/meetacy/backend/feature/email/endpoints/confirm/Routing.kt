package app.meetacy.backend.feature.email.endpoints.confirm

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.di.global.di
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

fun Route.confirmEmail() {
    val repository: ConfirmEmailRepository by di.getting

    post("/confirm") {
        val parameters = call.receive<ConfirmParams>()

        when (repository.checkConfirmHash(parameters.email, parameters.confirmHash)) {
            ConfirmHashResult.LinkExpired -> call.respondFailure(Failure.ExpiredLink)
            ConfirmHashResult.LinkInvalid -> call.respondFailure(Failure.InvalidLink)
            ConfirmHashResult.LinkMaxAttemptsReached -> call.respondFailure(Failure.LinkMaxAttemptsReached)
            ConfirmHashResult.Success -> call.respondSuccess()
        }
    }
}
