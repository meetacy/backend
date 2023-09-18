package app.meetacy.backend.feature.invitations.endpoints.integration.deny

import app.meetacy.backend.feature.invitations.endpoints.deny.DenyInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.deny.DenyInvitationResponse
import app.meetacy.backend.feature.invitations.endpoints.deny.InvitationDenyingFormSerializable
import app.meetacy.backend.feature.invitations.endpoints.deny.invitationDeny
import app.meetacy.backend.feature.invitations.usecase.deny.DenyInvitationUsecase
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.invitation.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.invitationDeny(di: DI) {
    val usecase: DenyInvitationUsecase by di.getting
    val repository = object : DenyInvitationRepository {
        override suspend fun denyInvitation(form: InvitationDenyingFormSerializable): DenyInvitationResponse =
            usecase.markAsDenied(form.token.type(), form.id.type()).toEndpoint()

        private fun DenyInvitationUsecase.Result.toEndpoint() = when (this) {
            DenyInvitationUsecase.Result.NoPermissions -> DenyInvitationResponse.NoPermissions
            DenyInvitationUsecase.Result.NotFound -> DenyInvitationResponse.NotFound
            DenyInvitationUsecase.Result.Success -> DenyInvitationResponse.Success
            DenyInvitationUsecase.Result.Unauthorized -> DenyInvitationResponse.Unauthorized
        }
    }
    invitationDeny(repository)
}
