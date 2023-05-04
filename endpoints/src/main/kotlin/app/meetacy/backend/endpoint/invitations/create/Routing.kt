package app.meetacy.backend.endpoint.invitations.create

import app.meetacy.backend.endpoint.invitations.crud.InvitationCreatingFormSerializable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createInvitationRouting() {
    post("/create") {
        val invitationCreatingForm: InvitationCreatingFormSerializable = call.receive()

        val response = createInvitation(invitationCreatingForm)

        val httpStatusCode = when (response) {
            is InvitationsCreateResponse.Success -> {
                HttpStatusCode.OK
            }
            InvitationsCreateResponse.UserNotFound -> {
                HttpStatusCode.NotFound
            }
            InvitationsCreateResponse.MeetingNotFound -> {
                HttpStatusCode.NotFound
            }
            InvitationsCreateResponse.NoPermissions -> {
                HttpStatusCode.MethodNotAllowed
            }
            InvitationsCreateResponse.Unauthorized -> {
                HttpStatusCode.Unauthorized
            }
            InvitationsCreateResponse.UserAlreadyInvited -> {
                HttpStatusCode.Conflict
            }
        }

        call.respond(httpStatusCode, if (response is InvitationsCreateResponse.Success) response else "")
    }
}

sealed interface InvitationsCreateResponse {
    data class Success(val response: String /* invitation ID */): InvitationsCreateResponse
    object Unauthorized: InvitationsCreateResponse
    object NoPermissions: InvitationsCreateResponse
    object UserAlreadyInvited: InvitationsCreateResponse
    object UserNotFound: InvitationsCreateResponse
    object MeetingNotFound: InvitationsCreateResponse
}

fun createInvitation(invitationCreatingForm: InvitationCreatingFormSerializable): InvitationsCreateResponse {
    TODO("Not yet implemented")
}