package app.meetacy.backend.feature.invitations.usecase.integration.invitations.cancel

import app.meetacy.backend.feature.invitations.endpoints.cancel.CancelInvitationForm
import app.meetacy.backend.feature.invitations.endpoints.cancel.CancelInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.cancel.CancelInvitationResponse
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.invitation.type
import app.meetacy.backend.feature.invitations.usecase.invitations.cancel.CancelInvitationUsecase

class UsecaseCancelInvitationRepository(
    private val usecase: CancelInvitationUsecase
): CancelInvitationRepository {

    override suspend fun cancel(form: CancelInvitationForm): CancelInvitationResponse =
        usecase.cancel(form.token.type(), form.id.type()).toEndpoint()

    private fun CancelInvitationUsecase.Result.toEndpoint(): CancelInvitationResponse = when (this) {
        CancelInvitationUsecase.Result.NotFound -> CancelInvitationResponse.NotFound
        CancelInvitationUsecase.Result.Success -> CancelInvitationResponse.Success
        CancelInvitationUsecase.Result.Unauthorized -> CancelInvitationResponse.Unauthorized
    }
}
