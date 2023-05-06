package app.meetacy.backend.usecase.integration.invitations.read

import app.meetacy.backend.endpoint.invitations.read.GetInvitationParams
import app.meetacy.backend.endpoint.invitations.read.InvitationsGetResponse
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
    override suspend fun getInvitation(getInvitationParams: GetInvitationParams): InvitationsGetResponse {
        with (getInvitationParams) {
            if (invitationIds == null && invitorUserIds == null) {
                return InvitationsGetResponse.SpecifyAtLeastOneParam
            }
            if (invitationIds != null && invitorUserIds != null) {
                return InvitationsGetResponse.OnlyUserIdsOrInvitationIdsAreAllowed
            }
        }
        val userIdentity = getInvitationParams.token.type()
        with (usecase) {
            if (getInvitationParams.invitationIds != null) {
                // retrieve invitations by invitation ids
                val invitationIds = (getInvitationParams.invitationIds as List<InvitationIdSerializable>)
                    .map { it.type() }
                return userIdentity.getInvitations(invitationIds).toGetResponse()
            }
            if (getInvitationParams.invitorUserIds != null) {
                // retrieve invitations by invitor ids
                val userIds = (getInvitationParams.invitorUserIds as List<UserIdSerializable>)
                    .map { it.type() }
                return userIdentity.getInvitations(from = userIds).toGetResponse()
            }
            return userIdentity.getInvitations().toGetResponse()
        }

    }

    private fun ReadInvitationUsecase.Result.toGetResponse(): InvitationsGetResponse = when (this) {
        ReadInvitationUsecase.Result.InvalidUserId -> InvitationsGetResponse.NotFound
        ReadInvitationUsecase.Result.InvitationsNotFound -> InvitationsGetResponse.InvalidInvitationIds
        is ReadInvitationUsecase.Result.Success -> InvitationsGetResponse.Success(this.invitations.map { it.toEndpoint() })
        ReadInvitationUsecase.Result.Unauthorized -> InvitationsGetResponse.Unauthorized
        ReadInvitationUsecase.Result.UsersNotFound -> InvitationsGetResponse.InvalidUserIds
    }

    private fun app.meetacy.backend.usecase.types.Invitation.toEndpoint(): Invitation {
        return Invitation(
            id.toString(),
            expiryDate.atStartOfDay.serializable(),
            invitedUserId.serializable(),
            invitorUserId.serializable(),
            meeting.serializable(),
            title,
            description
        )
    }
}
