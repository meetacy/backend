@file:UseSerializers(AccessTokenSerializer::class)

package app.meetacy.backend.endpoint.auth.email.link

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.serialization.AccessTokenSerializer
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class LinkParameters(
    val email: String,
    val accessToken: AccessToken
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
    suspend fun linkEmail(token: AccessToken, email: String): ConfirmHashResult
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
