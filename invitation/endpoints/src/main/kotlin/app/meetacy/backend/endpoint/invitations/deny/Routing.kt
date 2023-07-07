package app.meetacy.backend.endpoint.invitations.deny

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
data class InvitationDenyingFormSerializable(
    val id: InvitationIdSerializable,
    val token: AccessIdentitySerializable
)

fun Route.invitationDeny(invitationsDenyRepository: DenyInvitationRepository) {
    post("/deny") {
        val form: InvitationDenyingFormSerializable = call.receive()

        when (invitationsDenyRepository.denyInvitation(form)) {
            app.meetacy.backend.endpoint.invitations.deny.DenyInvitationResponse.Success -> {
                call.respondSuccess()
            }
            app.meetacy.backend.endpoint.invitations.deny.DenyInvitationResponse.Unauthorized -> {
                call.respondFailure(Failure.InvalidToken)
            }
            app.meetacy.backend.endpoint.invitations.deny.DenyInvitationResponse.NoPermissions -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
            app.meetacy.backend.endpoint.invitations.deny.DenyInvitationResponse.NotFound -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
        }
    }
}

interface DenyInvitationRepository{
    suspend fun denyInvitation(form: InvitationDenyingFormSerializable): DenyInvitationResponse
}

sealed interface DenyInvitationResponse {
    object Success: DenyInvitationResponse
    object Unauthorized: DenyInvitationResponse
    object NoPermissions: DenyInvitationResponse
    object NotFound: DenyInvitationResponse
}
