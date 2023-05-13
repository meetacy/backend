package app.meetacy.backend.usecase.integration.invitations.deny

import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationRepository
import app.meetacy.backend.endpoint.invitations.deny.InvitationDeletingFormSerializable
import app.meetacy.backend.endpoint.invitations.deny.InvitationsDeletionResponse
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase

class UsecaseDenyInvitationRepository(
    private val usecase: DenyInvitationUsecase
): DenyInvitationRepository {
    override suspend fun deleteInvitation(invitationDeletingForm: InvitationDeletingFormSerializable): InvitationsDeletionResponse =
        with(usecase) {
            invitationDeletingForm.id.type().markAsDenied(invitationDeletingForm.token.type()).toEndpoint()
        }

    private fun DenyInvitationUsecase.Result.toEndpoint() = when (this) {
        DenyInvitationUsecase.Result.UserNotFound -> InvitationsDeletionResponse.UserNotFound
        DenyInvitationUsecase.Result.NoPermissions -> InvitationsDeletionResponse.NoPermissions
        DenyInvitationUsecase.Result.NotFound -> InvitationsDeletionResponse.NotFound
        DenyInvitationUsecase.Result.Success -> InvitationsDeletionResponse.Success
        DenyInvitationUsecase.Result.Unauthorized -> InvitationsDeletionResponse.Unauthorized
    }
}