package app.meetacy.backend.usecase.integration.invitations.deny

import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationRepository
import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationResponse
import app.meetacy.backend.endpoint.invitations.deny.InvitationDenyingFormSerializable
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase

class UsecaseDenyInvitationRepository(
    private val usecase: DenyInvitationUsecase
): DenyInvitationRepository {
    override suspend fun denyInvitation(form: InvitationDenyingFormSerializable): DenyInvitationResponse =
        usecase.markAsDenied(form.token.type(), form.id.type()).toEndpoint()

    private fun DenyInvitationUsecase.Result.toEndpoint() = when (this) {
        DenyInvitationUsecase.Result.NoPermissions -> DenyInvitationResponse.NoPermissions
        DenyInvitationUsecase.Result.NotFound -> DenyInvitationResponse.NotFound
        DenyInvitationUsecase.Result.Success -> DenyInvitationResponse.Success
        DenyInvitationUsecase.Result.Unauthorized -> DenyInvitationResponse.Unauthorized
    }
}
