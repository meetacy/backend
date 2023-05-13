package app.meetacy.backend.usecase.integration.invitations.deny

import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationRepository
import app.meetacy.backend.endpoint.invitations.deny.InvitationDenyingFormSerializable
import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationResponse
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase

class UsecaseDenyInvitationRepository(
    private val usecase: DenyInvitationUsecase
): DenyInvitationRepository {
    override suspend fun deleteInvitation(invitationDenyingForm: InvitationDenyingFormSerializable): DenyInvitationResponse =
        with(usecase) {
            invitationDenyingForm.id.type().markAsDenied(invitationDenyingForm.token.type()).toEndpoint()
        }

    private fun DenyInvitationUsecase.Result.toEndpoint() = when (this) {
        DenyInvitationUsecase.Result.UserNotFound -> DenyInvitationResponse.UserNotFound
        DenyInvitationUsecase.Result.NoPermissions -> DenyInvitationResponse.NoPermissions
        DenyInvitationUsecase.Result.NotFound -> DenyInvitationResponse.NotFound
        DenyInvitationUsecase.Result.Success -> DenyInvitationResponse.Success
        DenyInvitationUsecase.Result.Unauthorized -> DenyInvitationResponse.Unauthorized
    }
}