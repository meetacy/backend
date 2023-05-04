package app.meetacy.backend.endpoint.invitations.accept

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class InvitationAcceptParams(
    val id: String,
    val reason: String
)

fun Route.invitationAcceptRouting() {
    post("/accept") {
        val acceptParams: InvitationAcceptParams = call.receive()

        val httpStatusCode = when (acceptInvitation(acceptParams)) {
            InvitationAcceptResponse.Success -> {
                HttpStatusCode.OK
            }
            InvitationAcceptResponse.NoPermissions -> {
                HttpStatusCode.MethodNotAllowed
            }
            InvitationAcceptResponse.Unauthorized -> {
                HttpStatusCode.Unauthorized
            }
            InvitationAcceptResponse.NotFound -> {
                HttpStatusCode.NotFound
            }
        }

        call.respond(httpStatusCode)
    }
}

// code below is needed to be implemented in use-cases/repositories/somewhere else
// and added just for getting rid of red lines in IDE
fun acceptInvitation(params: InvitationAcceptParams): InvitationAcceptResponse {
    TODO("Not yet implemented")
}

sealed interface InvitationAcceptResponse {
    object Success: InvitationAcceptResponse
    object NotFound: InvitationAcceptResponse
    object Unauthorized: InvitationAcceptResponse
    object NoPermissions: InvitationAcceptResponse
}