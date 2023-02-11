package app.meetacy.backend.endpoint.auth.email.link

import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class LinkParameters(
    val email: String,
    val accessIdentity: AccessIdentitySerializable
)

sealed interface ConfirmHashResult {
    object TokenInvalid : ConfirmHashResult
    object Success : ConfirmHashResult
}

interface LinkEmailRepository {
    suspend fun linkEmail(token: AccessIdentity, email: String): ConfirmHashResult
}

/**
 * TODO: check for *email* format
 */
fun Route.linkEmail(repository: LinkEmailRepository) = post("/link") {
    val parameters = call.receive<LinkParameters>()

    when (repository.linkEmail(parameters.accessIdentity.type(), parameters.email)) {
        is ConfirmHashResult.Success -> call.respondSuccess()
        is ConfirmHashResult.TokenInvalid ->
            call.respondFailure(1, "The token you've provided is invalid")
    }
}
