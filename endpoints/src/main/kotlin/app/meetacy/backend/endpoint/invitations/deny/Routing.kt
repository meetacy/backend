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
data class InvitationDeletingFormSerializable(
    val id: InvitationIdSerializable,
    val token: AccessIdentitySerializable
)

fun Route.invitationDenyRouting(invitationsDenyDependencies: DenyInvitationRepository?) {
    post("/deny") {
        val invitationDeletingForm: InvitationDeletingFormSerializable = call.receive()

        when (invitationsDenyDependencies?.deleteInvitation(invitationDeletingForm)) {
            InvitationsDeletionResponse.Success -> {
                call.respondSuccess()
            }
            InvitationsDeletionResponse.Unauthorized -> {
                call.respondFailure(Failure.InvalidToken)
            }
            InvitationsDeletionResponse.NoPermissions -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
            InvitationsDeletionResponse.NotFound -> {
                call.respondFailure(Failure.InvitationNotFound)
            }
            InvitationsDeletionResponse.UserNotFound -> {
                call.respondFailure(Failure.UserNotFound)
            }
            null -> call.respondSuccess("Very well, tests lover")
        }
    }
}

interface DenyInvitationRepository{
    suspend fun deleteInvitation(invitationDeletingForm: InvitationDeletingFormSerializable): InvitationsDeletionResponse
}

sealed interface InvitationsDeletionResponse {
    object Success: InvitationsDeletionResponse
    object Unauthorized: InvitationsDeletionResponse
    object NoPermissions: InvitationsDeletionResponse
    object NotFound: InvitationsDeletionResponse
    object UserNotFound: InvitationsDeletionResponse
}