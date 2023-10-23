package app.meetacy.backend.feature.invitations.endpoints.cancel

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity as AccessIdentitySerializable
import app.meetacy.backend.types.serializable.invitation.InvitationId
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CancelInvitationForm(
    val invitationId: InvitationId
)

fun Route.invitationCancel(invitationCancelRepository: CancelInvitationRepository) = post("/cancel") {
    val form: CancelInvitationForm = call.receive()
    val token = call.accessIdentity()

    with(invitationCancelRepository) {
        when (cancel(token, form.invitationId)) {
            CancelInvitationResponse.Success -> {
                call.respondSuccess()
            }
            CancelInvitationResponse.Unauthorized -> {
                call.respondFailure(Failure.InvalidToken)
            }
            CancelInvitationResponse.NotFound -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
        }
    }
}

interface CancelInvitationRepository {
    suspend fun cancel(token: AccessIdentitySerializable, invitationId: InvitationId): CancelInvitationResponse
}

sealed interface CancelInvitationResponse {
    data object Success : CancelInvitationResponse
    data object Unauthorized : CancelInvitationResponse
    data object NotFound : CancelInvitationResponse
}
