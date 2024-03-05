package app.meetacy.backend.feature.invitations.endpoints.create

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.invitation.Invitation
import app.meetacy.backend.types.serializable.meetings.MeetingId
import app.meetacy.backend.types.serializable.users.UserId
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import app.meetacy.backend.types.serializable.access.AccessIdentity

@Serializable
data class InvitationCreatingForm(
    val meetingId: MeetingId,
    val userId: UserId
)

fun Route.invitationCreate(invitationsCreateRepository: CreateInvitationRepository) = post("/create") {
    val form: InvitationCreatingForm = call.receive()
    val token = call.accessIdentity()

    when (val response = invitationsCreateRepository.createInvitation(token, form.meetingId, form.userId)) {
        is InvitationsCreateResponse.Success -> {
            call.respondSuccess(response.response)
        }
        InvitationsCreateResponse.UserNotFound -> {
            call.respondFailure(Failure.FriendNotFound)
        }
        InvitationsCreateResponse.MeetingNotFound -> {
            call.respondFailure(Failure.InvalidMeetingIdentity)
        }
        InvitationsCreateResponse.NoPermissions -> {
            call.respondFailure(Failure.UnableToInvite)
        }
        InvitationsCreateResponse.Unauthorized -> {
            call.respondFailure(Failure.InvalidToken)
        }
        InvitationsCreateResponse.UserAlreadyInvited -> {
            call.respondFailure(Failure.UserAlreadyInvited)
        }
    }
}

interface CreateInvitationRepository {
    suspend fun createInvitation(token: AccessIdentity, meetingId: MeetingId, userId: UserId): InvitationsCreateResponse
}

sealed interface InvitationsCreateResponse {
    @Serializable data class Success(val response: Invitation) : InvitationsCreateResponse
    data object Unauthorized : InvitationsCreateResponse
    data object NoPermissions : InvitationsCreateResponse
    data object UserAlreadyInvited : InvitationsCreateResponse
    data object UserNotFound : InvitationsCreateResponse
    data object MeetingNotFound : InvitationsCreateResponse
}
