package app.meetacy.backend.endpoint.invitations.create

import app.meetacy.backend.types.serialization.datetime.DateTimeSerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdSerializable
import app.meetacy.backend.types.serialization.user.UserIdSerializable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class InvitationCreatingFormSerializable(
    val meeting: MeetingIdSerializable,
    val invitedUser: UserIdSerializable,
    val expiryDate: DateTimeSerializable,
    val title: String,
    val description: String
)

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