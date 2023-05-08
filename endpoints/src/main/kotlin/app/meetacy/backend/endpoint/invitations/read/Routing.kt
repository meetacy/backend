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
data class GetInvitationParams(
    val token: AccessIdentitySerializable,
    val invitorUserIds: List<UserIdSerializable>?,
    val invitationIds: List<InvitationIdSerializable>?
)

fun Route.readInvitationRouting(readInvitationRepository: ReadInvitationRepository) {
    get("/read") {
        val invitationParams: GetInvitationParams = call.receive()
        val response = invitationParams.processRequest { params ->
            return@processRequest readInvitationRepository.getInvitation(params)
        }

        when (response) {
            is InvitationsGetResponse.Success -> {
                call.respondSuccess(response.response)
            }
            InvitationsGetResponse.InvalidInvitationIds -> {
                call.respondFailure(Failure.InvalidInvitationIds)
            }
            InvitationsGetResponse.InvalidUserIds -> {
                call.respondFailure(Failure.InvalidUserIds)
            }
            InvitationsGetResponse.OnlyUserIdsOrInvitationIdsAreAllowed -> {
                call.respondFailure(Failure.OnlyUserIdsOrInvitationIdsAreAllowed)
            }
            InvitationsGetResponse.Unauthorized -> {
                call.respondFailure(Failure.InvalidToken)
            }
        }
    }
}

suspend fun GetInvitationParams.processRequest(ifValid: suspend (GetInvitationParams) -> InvitationsGetResponse): InvitationsGetResponse {
    if (invitationIds != null && invitorUserIds != null) {
        return InvitationsGetResponse.OnlyUserIdsOrInvitationIdsAreAllowed
    }
    return ifValid(this)
}

interface ReadInvitationRepository {
    suspend fun getInvitation(getInvitationParams: GetInvitationParams): InvitationsGetResponse
}

sealed interface InvitationsGetResponse {
    data class Success(val response: List<Invitation>): InvitationsGetResponse
    object OnlyUserIdsOrInvitationIdsAreAllowed: InvitationsGetResponse
    object InvalidUserIds: InvitationsGetResponse
    object InvalidInvitationIds: InvitationsGetResponse
    object Unauthorized: InvitationsGetResponse
}