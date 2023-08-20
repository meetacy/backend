package app.meetacy.backend.feature.email.endpoints.link

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity

@Serializable
data class LinkParameters(
    val email: String,
    val token: AccessIdentity
)

sealed interface ConfirmHashResult {
    data object InvalidIdentity : ConfirmHashResult
    data object Success : ConfirmHashResult
}

interface LinkEmailRepository {
    suspend fun linkEmail(token: AccessIdentity, email: String): ConfirmHashResult
}

fun Route.linkEmail(repository: LinkEmailRepository) {
    post("/link") {
        val parameters = call.receive<LinkParameters>()

        when (repository.linkEmail(parameters.token, parameters.email)) {
            is ConfirmHashResult.Success -> call.respondSuccess()
            is ConfirmHashResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
        }
    }
}
