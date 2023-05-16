package app.meetacy.backend.endpoint.invitations.read

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.endpoint.types.Invitation
import app.meetacy.backend.types.serialization.access.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.invitation.InvitationIdSerializable
import app.meetacy.backend.types.serialization.user.UserIdSerializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ReadInvitationParams(
    val token: AccessIdentitySerializable,
    val invitorUserIds: List<UserIdSerializable>?,
    val invitationIds: List<InvitationIdSerializable>?
)

fun Route.readInvitation(readInvitationRepository: ReadInvitationRepository) {
    get("/read") {
        val invitationParams: ReadInvitationParams = call.receive()

        if (invitationParams.invitationIds != null && invitationParams.invitorUserIds != null) {
            call.respondFailure(Failure.OnlyUserIdsOrInvitationIdsAreAllowed)
            return@get
        }

        when (val response = readInvitationRepository.readInvitation(invitationParams)) {
            is InvitationsReadResponse.Success -> {
                call.respondSuccess(response.response)
            }
            InvitationsReadResponse.InvalidInvitationIds -> {
                call.respondFailure(Failure.InvalidInvitationIds)
            }
            InvitationsReadResponse.InvalidUserIds -> {
                call.respondFailure(Failure.InvalidUserIds)
            }
            InvitationsReadResponse.OnlyUserIdsOrInvitationIdsAreAllowed -> {
                call.respondFailure(Failure.OnlyUserIdsOrInvitationIdsAreAllowed)
            }
            InvitationsReadResponse.Unauthorized -> {
                call.respondFailure(Failure.InvalidToken)
            }
        }
    }
}

interface ReadInvitationRepository {
    suspend fun readInvitation(readInvitationParams: ReadInvitationParams): InvitationsReadResponse
}

sealed interface InvitationsReadResponse {
    data class Success(val response: List<Invitation>): InvitationsReadResponse
    object OnlyUserIdsOrInvitationIdsAreAllowed: InvitationsReadResponse
    object InvalidUserIds: InvitationsReadResponse
    object InvalidInvitationIds: InvitationsReadResponse
    object Unauthorized: InvitationsReadResponse
}
