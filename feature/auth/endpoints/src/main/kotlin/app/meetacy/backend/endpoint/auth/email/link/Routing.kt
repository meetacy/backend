package app.meetacy.backend.endpoint.auth.email.link

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable

@Serializable
data class LinkParameters(
    val email: String,
    val token: AccessIdentitySerializable
)

sealed interface ConfirmHashResult {
    data object InvalidIdentity : ConfirmHashResult
    data object Success : ConfirmHashResult
}

interface LinkEmailRepository {
    suspend fun linkEmail(token: AccessIdentity, email: String): ConfirmHashResult
}

/**
 * TODO: check for *email* format
 */
fun Route.linkEmail(repository: LinkEmailRepository) = post("/link") {
    val parameters = call.receive<LinkParameters>()

    when (repository.linkEmail(parameters.token.type(), parameters.email)) {
        is ConfirmHashResult.Success -> call.respondSuccess()
        is ConfirmHashResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
