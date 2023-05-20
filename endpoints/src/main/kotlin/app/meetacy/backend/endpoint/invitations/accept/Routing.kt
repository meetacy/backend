package app.meetacy.backend.endpoint.invitations.accept

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.invitation.InvitationIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class InvitationAcceptParams(
    val token: AccessIdentitySerializable,
    val invitationIdentity: InvitationIdentitySerializable,
)

fun Route.invitationAccept(invitationsAcceptDependencies: AcceptInvitationRepository) {
    post("/accept") {
        val acceptParams: InvitationAcceptParams = call.receive()

        when (invitationsAcceptDependencies.acceptInvitation(acceptParams)) {
            InvitationAcceptResponse.Success -> {
                call.respondSuccess()
            }
            InvitationAcceptResponse.Unauthorized -> {
                call.respondFailure(Failure.InvalidToken)
            }
            InvitationAcceptResponse.NotFound -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
            InvitationAcceptResponse.InvitationExpired -> {
                call.respondFailure(Failure.InvitationExpired)
            }
            InvitationAcceptResponse.MeetingNotFound -> {
                call.respondFailure(Failure.InvalidMeetingIdentity)
            }
        }
    }
}

interface AcceptInvitationRepository {
    suspend fun acceptInvitation(params: InvitationAcceptParams): InvitationAcceptResponse
}


sealed interface InvitationAcceptResponse {
    object Success: InvitationAcceptResponse
    object NotFound: InvitationAcceptResponse
    object Unauthorized: InvitationAcceptResponse
    object InvitationExpired : InvitationAcceptResponse
    object MeetingNotFound : InvitationAcceptResponse
}
