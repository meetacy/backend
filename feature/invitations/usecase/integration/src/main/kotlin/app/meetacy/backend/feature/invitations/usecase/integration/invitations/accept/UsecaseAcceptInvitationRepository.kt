package app.meetacy.backend.feature.invitations.usecase.integration.invitations.accept

import app.meetacy.backend.feature.invitations.endpoints.accept.AcceptInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.accept.InvitationAcceptParams
import app.meetacy.backend.feature.invitations.endpoints.accept.InvitationAcceptResponse
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.invitation.type
import app.meetacy.backend.feature.invitations.usecase.AcceptInvitationUsecase

class UsecaseAcceptInvitationRepository(
    private val usecase: AcceptInvitationUsecase
): AcceptInvitationRepository {
    override suspend fun acceptInvitation(params: InvitationAcceptParams): InvitationAcceptResponse =
        usecase.accept(params.token.type(), params.id.type()).toEndpoint()


    private fun AcceptInvitationUsecase.Result.toEndpoint(): InvitationAcceptResponse = when (this) {
        AcceptInvitationUsecase.Result.NotFound -> InvitationAcceptResponse.NotFound
        AcceptInvitationUsecase.Result.Success -> InvitationAcceptResponse.Success
        AcceptInvitationUsecase.Result.Unauthorized -> InvitationAcceptResponse.Unauthorized
        AcceptInvitationUsecase.Result.MeetingNotFound -> InvitationAcceptResponse.MeetingNotFound
    }
}
