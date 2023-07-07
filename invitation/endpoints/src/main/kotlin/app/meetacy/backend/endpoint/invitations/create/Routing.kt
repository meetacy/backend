package app.meetacy.backend.endpoint.invitations.create

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Invitation
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdentitySerializable
import app.meetacy.backend.types.serialization.user.UserIdentitySerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class InvitationCreatingFormSerializable(
    val token: AccessIdentitySerializable,
    val meetingId: MeetingIdentitySerializable,
    val userId: UserIdentitySerializable
)

fun Route.invitationCreate(invitationsCreateRepository: CreateInvitationRepository) {
    post("/create") {
        val invitationCreatingForm: InvitationCreatingFormSerializable = call.receive()

        when (val response = invitationsCreateRepository.createInvitation(invitationCreatingForm)) {
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
}

interface CreateInvitationRepository {
    suspend fun createInvitation(form: InvitationCreatingFormSerializable): InvitationsCreateResponse
}

sealed interface InvitationsCreateResponse {
    @Serializable data class Success(val response: Invitation) : InvitationsCreateResponse
    object Unauthorized : InvitationsCreateResponse
    object NoPermissions : InvitationsCreateResponse
    object UserAlreadyInvited : InvitationsCreateResponse
    object UserNotFound : InvitationsCreateResponse
    object MeetingNotFound : InvitationsCreateResponse
}
