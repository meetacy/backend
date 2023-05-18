package app.meetacy.backend.usecase.integration.invitations.read

import app.meetacy.backend.endpoint.invitations.read.InvitationsReadResponse
import app.meetacy.backend.endpoint.invitations.read.ReadInvitationParams
import app.meetacy.backend.endpoint.invitations.read.ReadInvitationRepository
import app.meetacy.backend.endpoint.types.Invitation
import app.meetacy.backend.types.serialization.datetime.serializable
import app.meetacy.backend.types.serialization.invitation.InvitationIdSerializable
import app.meetacy.backend.types.serialization.meeting.serializable
import app.meetacy.backend.types.serialization.user.UserIdSerializable
import app.meetacy.backend.types.serialization.user.serializable
import app.meetacy.backend.usecase.invitations.read.ReadInvitationUsecase

class UsecaseReadInvitationRepository(
    private val usecase: ReadInvitationUsecase
): ReadInvitationRepository {
    override suspend fun readInvitation(readInvitationParams: ReadInvitationParams): InvitationsReadResponse {

        val userIdentity = readInvitationParams.token.type()
        if (readInvitationParams.invitationIds != null) {
            // retrieve invitations by invitation ids
            val invitationIds = (readInvitationParams.invitationIds as List<InvitationIdSerializable>)
                .map { it.type() }
            return usecase.getInvitationsByIds(invitationIds, token = userIdentity).toReadResponse()
        }
        if (readInvitationParams.invitorUserIds != null) {
            // retrieve invitations by invitor ids
            val userIds = (readInvitationParams.invitorUserIds as List<UserIdSerializable>)
                .map { it.type() }
            return usecase.getInvitations(from = userIds, token = userIdentity).toReadResponse()
        }
        return usecase.getInvitations(userIdentity).toReadResponse()

    }

    private fun ReadInvitationUsecase.Result.toReadResponse(): InvitationsReadResponse = when (this) {
        ReadInvitationUsecase.Result.InvitationsNotFound -> InvitationsReadResponse.InvalidInvitationIds
        is ReadInvitationUsecase.Result.Success -> InvitationsReadResponse.Success(this.invitations.map { it.toEndpoint() })
        ReadInvitationUsecase.Result.Unauthorized -> InvitationsReadResponse.Unauthorized
        ReadInvitationUsecase.Result.UsersNotFound -> InvitationsReadResponse.InvalidUserIds
    }

    private fun app.meetacy.backend.usecase.types.Invitation.toEndpoint(): Invitation {
        return Invitation(
            id.toString(),
            expiryDate.serializable(),
            invitedUserId.serializable(),
            invitorUserId.serializable(),
            meeting.serializable()
        )
    }
}
