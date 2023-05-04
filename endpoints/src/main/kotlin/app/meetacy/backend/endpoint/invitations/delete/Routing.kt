package app.meetacy.backend.endpoint.invitations.delete

import app.meetacy.backend.endpoint.invitations.crud.InvitationDeletingFormSerializable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.invitationDeleteRouting() {
    delete("/delete") {
        val invitationDeletingForm: InvitationDeletingFormSerializable = call.receive()

        val httpStatusCode: HttpStatusCode = when (deleteInvitation(invitationDeletingForm)) {
            InvitationsDeletionResponse.Success -> {
                HttpStatusCode.OK
            }

            InvitationsDeletionResponse.Unauthorized -> {
                HttpStatusCode.Unauthorized
            }

            InvitationsDeletionResponse.NoPermissions -> {
                HttpStatusCode.MethodNotAllowed
            }

            InvitationsDeletionResponse.NotFound -> {
                HttpStatusCode.NotFound
            }
        }
        call.respond(httpStatusCode)
    }
}

fun deleteInvitation(invitationDeletingForm: InvitationDeletingFormSerializable): InvitationsDeletionResponse {
    TODO("Not yet implemented")
}

sealed interface InvitationsDeletionResponse {
    object Success: InvitationsDeletionResponse
    object Unauthorized: InvitationsDeletionResponse
    object NoPermissions: InvitationsDeletionResponse
    object NotFound: InvitationsDeletionResponse
}