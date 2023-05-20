package app.meetacy.backend.usecase.integration.invitations.cancel

import app.meetacy.backend.endpoint.invitations.cancel.CancelInvitationForm
import app.meetacy.backend.endpoint.invitations.cancel.CancelInvitationRepository
import app.meetacy.backend.endpoint.invitations.cancel.CancelInvitationResponse
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase

class UsecaseCancelInvitationRepository(
    private val usecase: CancelInvitationUsecase
): CancelInvitationRepository {

    override suspend fun cancel(form: CancelInvitationForm): CancelInvitationResponse =
        usecase.cancel(form.token.type(), form.invitationIdentity.type()).toEndpoint()

    private fun CancelInvitationUsecase.Result.toEndpoint(): CancelInvitationResponse = when (this) {
        CancelInvitationUsecase.Result.NoPermissions -> CancelInvitationResponse.NoPermissions
        CancelInvitationUsecase.Result.NotFound -> CancelInvitationResponse.NotFound
        CancelInvitationUsecase.Result.Success -> CancelInvitationResponse.Success
        CancelInvitationUsecase.Result.Unauthorized -> CancelInvitationResponse.Unauthorized
    }
}
