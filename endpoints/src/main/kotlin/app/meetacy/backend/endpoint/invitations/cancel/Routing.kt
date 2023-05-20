package app.meetacy.backend.endpoint.invitations.cancel

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
data class CancelInvitationForm(
    val token: AccessIdentitySerializable,
    val invitationIdentity: InvitationIdentitySerializable
)

fun Route.invitationCancel(invitationCancelRepository: CancelInvitationRepository) = post("/cancel") {
    val form: CancelInvitationForm = call.receive()

    with(invitationCancelRepository) {
        when (cancel(form)) {
            CancelInvitationResponse.Success -> {
                call.respondSuccess()
            }
            CancelInvitationResponse.Unauthorized -> {
                call.respondFailure(Failure.InvalidToken)
            }
            CancelInvitationResponse.NoPermissions -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
            CancelInvitationResponse.NotFound -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
        }
    }
}

interface CancelInvitationRepository {
    suspend fun cancel(form: CancelInvitationForm): CancelInvitationResponse
}

sealed interface CancelInvitationResponse {
    object Success: CancelInvitationResponse
    object Unauthorized: CancelInvitationResponse
    object NoPermissions: CancelInvitationResponse
    object NotFound: CancelInvitationResponse
}
