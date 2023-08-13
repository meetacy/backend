package app.meetacy.backend.feature.invitations.usecase.integration.invitations.deny

import app.meetacy.backend.feature.invitations.endpoints.deny.DenyInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.deny.DenyInvitationResponse
import app.meetacy.backend.feature.invitations.endpoints.deny.InvitationDenyingFormSerializable
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.invitation.type
import app.meetacy.backend.feature.invitations.usecase.invitations.deny.DenyInvitationUsecase

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
