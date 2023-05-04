package app.meetacy.backend.endpoint.invitations.update

import app.meetacy.backend.types.serialization.datetime.DateTimeSerializable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class InvitationUpdatingFormSerializable(
    val id: String, /* invitation ID */
    val expiryDate: DateTimeSerializable,
    val title: String,
    val description: String
)

fun Route.invitationUpdateRouting() {
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

fun updateInvitation(invitationUpdatingForm: InvitationUpdatingFormSerializable): InvitationsUpdateResponse {
    TODO("Not yet implemented")
}

sealed interface InvitationsUpdateResponse {
    data class Success(val response: String /* invitation ID */): InvitationsUpdateResponse
    object Unauthorized: InvitationsUpdateResponse
    object NoPermissions: InvitationsUpdateResponse
    object NotFound: InvitationsUpdateResponse /* invitation not found */
}