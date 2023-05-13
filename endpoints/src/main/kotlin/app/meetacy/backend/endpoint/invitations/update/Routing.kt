package app.meetacy.backend.endpoint.invitations.update

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.datetime.DateSerializable
import app.meetacy.backend.types.serialization.invitation.InvitationIdSerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class InvitationUpdatingFormSerializable(
    val token: AccessIdentitySerializable,
    val id: InvitationIdSerializable,
    val title: String?,
    val description: String?,
    val expiryDate: DateSerializable?,
    val meetingId: MeetingIdSerializable
)

fun Route.invitationUpdateRouting(invitationUpdateRepository: InvitationUpdateRepository) =
    post("/update") {
        val form: InvitationUpdatingFormSerializable = call.receive()

        when (invitationUpdateRepository.update(form = form)) {
            InvitationsUpdateResponse.Success -> call.respondSuccess(form.id)
            InvitationsUpdateResponse.Unauthorized -> call.respondFailure(Failure.InvalidToken)
            InvitationsUpdateResponse.InvalidData -> call.respondFailure(Failure.NullEditParams)
            InvitationsUpdateResponse.InvitationNotFound -> call.respondFailure(Failure.InvitationNotFound)
            InvitationsUpdateResponse.MeetingNotFound -> call.respondFailure(Failure.InvalidMeetingIdentity)
        }
    }

interface InvitationUpdateRepository {
    suspend fun update(form: InvitationUpdatingFormSerializable): InvitationsUpdateResponse
}

sealed interface InvitationsUpdateResponse {
    object Success: InvitationsUpdateResponse
    object Unauthorized: InvitationsUpdateResponse
    object InvitationNotFound: InvitationsUpdateResponse
    object InvalidData: InvitationsUpdateResponse
    object MeetingNotFound: InvitationsUpdateResponse
}