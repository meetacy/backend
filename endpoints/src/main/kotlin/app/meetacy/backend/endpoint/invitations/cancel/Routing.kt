package app.meetacy.backend.endpoint.invitations.cancel

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.invitation.InvitationIdSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CancelInvitationForm(
    val token: AccessIdentitySerializable,
    val invitationId: InvitationIdSerializable
)

fun Route.invitationCancelRouting(invitationCancelRepository: CancelInvitationRepository?) = post("/delete") {
    val form: CancelInvitationForm = call.receive()

    with(invitationCancelRepository) {
        if (this != null) {
            when (form.cancel()) {
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
                CancelInvitationResponse.UserNotFound -> {
                    call.respondFailure(Failure.UserNotFound)
                }
            }
        } else {
            call.respond("Very well, tests lover")
        }
    }
}

interface CancelInvitationRepository {
    fun CancelInvitationForm.cancel(): CancelInvitationResponse
}

sealed interface CancelInvitationResponse {
    object Success: CancelInvitationResponse
    object Unauthorized: CancelInvitationResponse
    object NoPermissions: CancelInvitationResponse
    object NotFound: CancelInvitationResponse
    object UserNotFound: CancelInvitationResponse
}