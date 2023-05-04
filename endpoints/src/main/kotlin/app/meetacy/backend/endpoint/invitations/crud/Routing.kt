package app.meetacy.backend.endpoint.invitations.crud

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
data class GetInvitationParams(
    val id: String,
)

@Serializable
data class InvitationCreatingFormSerializable(
    val meeting: MeetingIdSerializable,
    val invitedUser: UserIdSerializable,
    val expiryDate: DateTimeSerializable,
    val title: String,
    val description: String
)

@Serializable
data class InvitationUpdatingFormSerializable(
    val id: String, /* invitation ID */
    val expiryDate: DateTimeSerializable,
    val title: String,
    val description: String
)

@Serializable
data class InvitationDeletingFormSerializable(
    val id: String, /* again, invitation ID */
)

fun Route.crudInvitationRouting() {


    put("/update") {
        val invitationUpdatingForm: InvitationUpdatingFormSerializable = call.receive()

        val response = updateInvitation(invitationUpdatingForm)

        val httpStatusCode = when (response) {
            is InvitationsUpdateResponse.Success -> {
                HttpStatusCode.OK
            }

            InvitationsUpdateResponse.Unauthorized -> {
                HttpStatusCode.Unauthorized
            }

            InvitationsUpdateResponse.NoPermissions -> {
                HttpStatusCode.MethodNotAllowed
            }

            InvitationsUpdateResponse.NotFound -> {
                HttpStatusCode.NotFound
            }
        }

        call.respond(httpStatusCode, if (response is InvitationsUpdateResponse.Success) response else "")
    }

}

// code below is needed to be implemented in use-cases/repositories/somewhere else
// and added just for getting rid of red lines in IDE



fun updateInvitation(invitationUpdatingForm: InvitationUpdatingFormSerializable): InvitationsUpdateResponse {
    TODO("Not yet implemented")
}





sealed interface InvitationsUpdateResponse {
    data class Success(val response: String /* invitation ID */): InvitationsUpdateResponse
    object Unauthorized: InvitationsUpdateResponse
    object NoPermissions: InvitationsUpdateResponse
    object NotFound: InvitationsUpdateResponse /* invitation not found */
}
