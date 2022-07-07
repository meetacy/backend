package app.meetacy.backend.endpoint.auth.email.link

import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

@Serializable
private data class LinkParameters(
    val email: String,
    val token: String
)

@Serializable
private data class LinkResponse(
    val status: Boolean = true
)

interface Mailer {
    fun sendConfirmEmail(email: String, confirmHash: String)
}

interface LinkEmailStorage {
    suspend fun registerConfirmHash(token: String, email: String): String
}

/**
 * TODO: check for *email* format
 */
fun Route.linkEmail(
    mailer: Mailer,
    storage: LinkEmailStorage
) = post("/link") {
    val parameters = call.receive<LinkParameters>()

    val confirmHash = storage.registerConfirmHash(parameters.token, parameters.email)
    mailer.sendConfirmEmail(parameters.email, confirmHash)

    call.respond(LinkResponse())
}
