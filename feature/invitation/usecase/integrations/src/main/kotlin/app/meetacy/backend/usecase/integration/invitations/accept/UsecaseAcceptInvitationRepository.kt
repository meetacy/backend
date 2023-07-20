package app.meetacy.backend.usecase.integration.invitations.accept

import app.meetacy.backend.endpoint.invitations.accept.AcceptInvitationRepository
import app.meetacy.backend.endpoint.invitations.accept.InvitationAcceptParams
import app.meetacy.backend.endpoint.invitations.accept.InvitationAcceptResponse
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase

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
