package app.meetacy.backend.feature.invitations.endpoints.deny

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.invitation.InvitationId
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity

@Serializable
data class InvitationDenyingFormSerializable(
    val invitationId: InvitationId
)

fun Route.invitationDeny(invitationsDenyRepository: DenyInvitationRepository) {
    post("/deny") {
        val form: InvitationDenyingFormSerializable = call.receive()

        val token = call.accessIdentity()

        when (invitationsDenyRepository.denyInvitation(token, form.invitationId)) {
            DenyInvitationResponse.Success -> {
                call.respondSuccess()
            }
            DenyInvitationResponse.Unauthorized -> {
                call.respondFailure(Failure.InvalidToken)
            }
            DenyInvitationResponse.NoPermissions -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
            DenyInvitationResponse.NotFound -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
        }
    }
}

interface DenyInvitationRepository{
    suspend fun denyInvitation(token: AccessIdentity, invitationId: InvitationId): DenyInvitationResponse
}

sealed interface DenyInvitationResponse {
    data object Success: DenyInvitationResponse
    data object Unauthorized: DenyInvitationResponse
    data object NoPermissions: DenyInvitationResponse
    data object NotFound: DenyInvitationResponse
}
