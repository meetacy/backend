package app.meetacy.backend.endpoint.invitations.update

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Invitation
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.datetime.DateTimeSerializable
import app.meetacy.backend.types.serialization.invitation.InvitationIdentitySerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class InvitationUpdatingFormSerializable(
    val token: AccessIdentitySerializable,
    val id: InvitationIdentitySerializable,
    val expiryDate: DateTimeSerializable?,
    val meetingId: MeetingIdentitySerializable?
)

fun Route.invitationUpdate(invitationUpdateRepository: InvitationUpdateRepository) =
    post("/update") {
        val form: InvitationUpdatingFormSerializable = call.receive()

        if (listOf(form.expiryDate, form.meetingId).all { it == null }) {
            call.respond(Failure.NullEditParams)
        }

        when (val response = invitationUpdateRepository.update(form = form)) {
            is InvitationsUpdateResponse.Success -> call.respondSuccess(response.invitation)
            InvitationsUpdateResponse.Unauthorized -> call.respondFailure(Failure.InvalidToken)
            InvitationsUpdateResponse.InvitationNotFound -> call.respondFailure(Failure.InvitationNotFound)
            InvitationsUpdateResponse.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
            InvitationsUpdateResponse.InvalidDateTimeIdentity -> call.respondFailure(Failure.InvalidDateTimeIdentity)
        }
    }

interface InvitationUpdateRepository {
    suspend fun update(form: InvitationUpdatingFormSerializable): InvitationsUpdateResponse
}

sealed interface InvitationsUpdateResponse {
    data class Success(val invitation: Invitation): InvitationsUpdateResponse
    object Unauthorized: InvitationsUpdateResponse
    object InvitationNotFound: InvitationsUpdateResponse
    object MeetingNotFound: InvitationsUpdateResponse
    object InvalidDateTimeIdentity: InvitationsUpdateResponse
}
