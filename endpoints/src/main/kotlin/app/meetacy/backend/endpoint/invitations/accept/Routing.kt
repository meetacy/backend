package app.meetacy.backend.endpoint.invitations.accept

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.invitation.InvitationIdSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class InvitationAcceptDeclineParams(
    val token: AccessIdentitySerializable,
    val invitationId: InvitationIdSerializable,
)

fun Route.invitationAcceptRouting(invitationsAcceptDependencies: InvitationAcceptRepository?) {
    post("/accept") {
        val acceptParams: InvitationAcceptDeclineParams = call.receive()

        when (invitationsAcceptDependencies!!.acceptInvitation(acceptParams)) {
            InvitationAcceptResponse.Success -> {
                call.respondSuccess()
            }
            InvitationAcceptResponse.Unauthorized -> {
                call.respondFailure(Failure.InvalidToken)
            }
            InvitationAcceptResponse.NotFound -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
        }
    }
}

interface InvitationAcceptRepository {
    suspend fun acceptInvitation(params: InvitationAcceptDeclineParams): InvitationAcceptResponse
}


sealed interface InvitationAcceptResponse {
    object Success: InvitationAcceptResponse
    object NotFound: InvitationAcceptResponse
    object Unauthorized: InvitationAcceptResponse
}