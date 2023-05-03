package app.meetacy.backend.endpoint.invitations.crud

import app.meetacy.backend.endpoint.types.Invitation
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

fun Route.crudInvitationRouting() {
    get("/get") {
        val invitationParams: GetInvitationParams = call.receive()

        val httpStatusCode: HttpStatusCode

        when (val invitationsGetResponse = getInvitation(invitationParams)) {
            is InvitationsGetResponse.Success -> {
                httpStatusCode = HttpStatusCode.OK
                call.respond(httpStatusCode, invitationsGetResponse)
            }
            InvitationsGetResponse.NoPermissions -> {
                httpStatusCode = HttpStatusCode.MethodNotAllowed
                call.respond(httpStatusCode, "Probably, you are neither invitor nor invited person")
            }
            InvitationsGetResponse.NotFound -> {
                httpStatusCode = HttpStatusCode.NotFound
                call.respond(httpStatusCode, "Invitation not found")
            }
        }
    }
    post("/create") {
        val invitationCreatingForm: InvitationCreatingFormSerializable = call.receive()
        val httpStatusCode: HttpStatusCode

        when (val response = createInvitation(invitationCreatingForm)) {
            is InvitationsCreateResponse.Success -> {
                httpStatusCode = HttpStatusCode.OK
                call.respond(httpStatusCode, response)
            }
            InvitationsCreateResponse.UserNotFound -> {
                httpStatusCode = HttpStatusCode.NotFound
                call.respond(httpStatusCode)
            }
            InvitationsCreateResponse.MeetingNotFound -> {
                httpStatusCode = HttpStatusCode.NotFound
                call.respond(httpStatusCode)
            }
            InvitationsCreateResponse.NoPermissions -> {
                httpStatusCode = HttpStatusCode.MethodNotAllowed
                call.respond(httpStatusCode)
            }
            InvitationsCreateResponse.Unauthorized -> {
                httpStatusCode = HttpStatusCode.Unauthorized
                call.respond(httpStatusCode)
            }
            InvitationsCreateResponse.UserAlreadyInvited -> {
                httpStatusCode = HttpStatusCode.Conflict
                call.respond(httpStatusCode)
            }
        }
    }
    put("/update") {

    }
    delete("/delete") {

    }
}

// code below is needed to be implemented in use-cases/repositories/somewhere else
// and added just for getting rid of red lines in IDE
fun getInvitation(getInvitationParams: GetInvitationParams): InvitationsGetResponse {
    TODO("Not yet implemented")
}

fun createInvitation(invitationCreatingForm: InvitationCreatingFormSerializable): InvitationsCreateResponse {
    TODO("Not yet implemented")
}

sealed interface InvitationsGetResponse {
    data class Success(val response: Invitation): InvitationsGetResponse
    object NoPermissions: InvitationsGetResponse
    object NotFound: InvitationsGetResponse
}

sealed interface InvitationsCreateResponse {
    data class Success(val response: String /* invitation ID */): InvitationsCreateResponse
    object Unauthorized: InvitationsCreateResponse
    object NoPermissions: InvitationsCreateResponse
    object UserAlreadyInvited: InvitationsCreateResponse
    object UserNotFound: InvitationsCreateResponse
    object MeetingNotFound: InvitationsCreateResponse
}
