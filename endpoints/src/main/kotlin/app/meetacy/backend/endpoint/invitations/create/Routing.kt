package app.meetacy.backend.endpoint.invitations.create

import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
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
    val token: AccessIdentitySerializable,
    val meeting: MeetingIdSerializable,
    val invitedUser: UserIdSerializable,
    val expiryDate: DateTimeSerializable,
    val title: String?,
    val description: String?
)

fun Route.createInvitationRouting(invitationsCreateDependencies: CreateInvitationRepository) {
    post("/create") {
        val invitationCreatingForm: InvitationCreatingFormSerializable = call.receive()

        val response = invitationsCreateDependencies.createInvitation(invitationCreatingForm)

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

            else -> {
                HttpStatusCode.BadRequest
            }
        }

        call.respond(httpStatusCode, if (response is InvitationsCreateResponse.Success) response.response else "")
    }
}

interface CreateInvitationRepository {
    suspend fun createInvitation(invitationCreatingForm: InvitationCreatingFormSerializable): InvitationsCreateResponse
}

sealed interface InvitationsCreateResponse {
    data class Success(val response: InvitationId): InvitationsCreateResponse
    object Unauthorized: InvitationsCreateResponse
    object NoPermissions: InvitationsCreateResponse
    object UserAlreadyInvited: InvitationsCreateResponse
    object UserNotFound: InvitationsCreateResponse
    object MeetingNotFound: InvitationsCreateResponse
    object InvalidData: InvitationsCreateResponse
}