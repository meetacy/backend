package app.meetacy.backend.usecase.integration.invitations.accept

import app.meetacy.backend.endpoint.invitations.accept.InvitationAcceptDeclineParams
import app.meetacy.backend.endpoint.invitations.accept.InvitationAcceptRepository
import app.meetacy.backend.endpoint.invitations.accept.InvitationAcceptResponse
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase

class UsecaseAcceptInvitationRepository(
    private val usecase: AcceptInvitationUsecase
): InvitationAcceptRepository {
    override suspend fun acceptInvitation(params: InvitationAcceptDeclineParams): InvitationAcceptResponse {
        TODO()
    }
}