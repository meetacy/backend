package app.meetacy.backend.feature.invitations.endpoints.accept

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.invitation.InvitationId
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable

@Serializable
data class InvitationAcceptParams(
    val token: AccessIdentitySerializable,
    val id: InvitationId,
)

fun Route.invitationAccept(invitationsAcceptRepository: AcceptInvitationRepository) {
    post("/accept") {
        val acceptParams: InvitationAcceptParams = call.receive()

        when (invitationsAcceptRepository.acceptInvitation(acceptParams)) {
            InvitationAcceptResponse.Success -> {
                call.respondSuccess()
            }
            InvitationAcceptResponse.Unauthorized -> {
                call.respondFailure(Failure.InvalidToken)
            }
            InvitationAcceptResponse.NotFound -> {
                call.respondFailure(Failure.InvitationNotFound)
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
    data object Success: InvitationAcceptResponse
    data object NotFound: InvitationAcceptResponse
    data object Unauthorized: InvitationAcceptResponse
    data object MeetingNotFound : InvitationAcceptResponse
}
