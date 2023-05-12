package app.meetacy.backend.usecase.integration.invitations.accept

import app.meetacy.backend.endpoint.invitations.accept.InvitationAcceptParams
import app.meetacy.backend.endpoint.invitations.accept.InvitationAcceptRepository
import app.meetacy.backend.endpoint.invitations.accept.InvitationAcceptResponse
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase

class UsecaseAcceptInvitationRepository(
    private val usecase: AcceptInvitationUsecase
): InvitationAcceptRepository {
    override suspend fun acceptInvitation(params: InvitationAcceptParams): InvitationAcceptResponse =
        with(usecase) {
            params.token.type().addToMeetingByInvitation(params.invitationId.type()).toEndpoint()
        }

    private fun AcceptInvitationUsecase.Result.toEndpoint(): InvitationAcceptResponse = when (this) {
        AcceptInvitationUsecase.Result.NotFound -> InvitationAcceptResponse.NotFound
        AcceptInvitationUsecase.Result.Success -> InvitationAcceptResponse.Success
        AcceptInvitationUsecase.Result.Unauthorized -> InvitationAcceptResponse.Unauthorized
        AcceptInvitationUsecase.Result.InvitationExpired -> InvitationAcceptResponse.InvitationExpired
        AcceptInvitationUsecase.Result.MeetingNotFound -> InvitationAcceptResponse.MeetingNotFound
    }
}
