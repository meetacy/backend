package app.meetacy.backend.endpoint.invitations.accept

import app.meetacy.backend.endpoint.invitations.InvitationsAcceptationDependencies
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class InvitationAcceptDeclineParams(
    val id: String,
    val reason: String
)

fun Route.invitationAcceptRouting(invitationsAcceptDependencies: InvitationsAcceptationDependencies?) {
    post("/accept") {
        val acceptParams: InvitationAcceptDeclineParams = call.receive()

        val httpStatusCode = when (acceptInvitation(acceptParams)) {
            InvitationAcceptDeclineResponse.Success -> {
                HttpStatusCode.OK
            }
            InvitationAcceptDeclineResponse.NoPermissions -> {
                HttpStatusCode.MethodNotAllowed
            }
            InvitationAcceptDeclineResponse.Unauthorized -> {
                HttpStatusCode.Unauthorized
            }
            InvitationAcceptDeclineResponse.NotFound -> {
                HttpStatusCode.NotFound
            }
        }

        call.respond(httpStatusCode)
    }
    post("/decline") {
        val acceptParams: InvitationAcceptDeclineParams = call.receive()

        val httpStatusCode = when (declineInvitation(acceptParams)) {
            InvitationAcceptDeclineResponse.Success -> {
                HttpStatusCode.OK
            }
            InvitationAcceptDeclineResponse.NoPermissions -> {
                HttpStatusCode.MethodNotAllowed
            }
            InvitationAcceptDeclineResponse.Unauthorized -> {
                HttpStatusCode.Unauthorized
            }
            InvitationAcceptDeclineResponse.NotFound -> {
                HttpStatusCode.NotFound
            }
        }

        call.respond(httpStatusCode)
    }
}

// code below is needed to be implemented in use-cases/repositories/somewhere else
// and added just for getting rid of red lines in IDE
fun acceptInvitation(params: InvitationAcceptDeclineParams): InvitationAcceptDeclineResponse {
    TODO("Not yet implemented")
}

fun declineInvitation(params: InvitationAcceptDeclineParams): InvitationAcceptDeclineResponse {
    TODO("Not yet implemented")
}

sealed interface InvitationAcceptDeclineResponse {
    object Success: InvitationAcceptDeclineResponse
    object NotFound: InvitationAcceptDeclineResponse
    object Unauthorized: InvitationAcceptDeclineResponse
    object NoPermissions: InvitationAcceptDeclineResponse
}