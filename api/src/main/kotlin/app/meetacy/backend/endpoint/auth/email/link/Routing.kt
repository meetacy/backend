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
    val token: String
)

@Serializable
data class LinkResponse(
    val status: Boolean = true,
    val errorCode: Int? = null,
    val errorMessage: String? = null
)

interface Mailer {
    fun sendConfirmEmail(email: String, confirmHash: String)
}

sealed interface ConfirmHashResult {
    object TokenInvalid : ConfirmHashResult
    class Success(val confirmHash: String) : ConfirmHashResult
}

interface LinkEmailStorage {
    suspend fun registerConfirmHash(token: String, email: String): ConfirmHashResult
}

/**
 * TODO: check for *email* format
 */
fun Route.linkEmail(
    mailer: Mailer,
    storage: LinkEmailStorage
) = post("/link") {
    val parameters = call.receive<LinkParameters>()

    when (
        val result = storage.registerConfirmHash(parameters.token, parameters.email)
    ) {
        is ConfirmHashResult.Success -> {
            mailer.sendConfirmEmail(parameters.email, result.confirmHash)
            call.respond(LinkResponse())
        }
        is ConfirmHashResult.TokenInvalid -> {
            call.respond(
                LinkResponse(
                    status = false,
                    errorCode = 1,
                    errorMessage = "The token you've provided is invalid"
                )
            )
        }
    }
}
