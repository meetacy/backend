package app.meetacy.backend.endpoint.auth.email.link

import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

@Serializable
data class LinkParameters(
    val email: String,
    val accessToken: String
)

@Serializable
data class LinkResponse(
    val status: Boolean,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

sealed interface ConfirmHashResult {
    object TokenInvalid : ConfirmHashResult
    object Success : ConfirmHashResult
}

interface LinkEmailRepository {
    suspend fun linkEmail(token: String, email: String): ConfirmHashResult
}

/**
 * TODO: check for *email* format
 */
fun Route.linkEmail(repository: LinkEmailRepository) = post("/link") {
    val parameters = call.receive<LinkParameters>()

    when (repository.linkEmail(parameters.accessToken, parameters.email)) {
        is ConfirmHashResult.Success -> call.respond(LinkResponse(status = true))
        is ConfirmHashResult.TokenInvalid ->
            call.respond(
                LinkResponse(
                    status = false,
                    errorCode = 1,
                    errorMessage = "The token you've provided is invalid"
                )
            )
    }
}
