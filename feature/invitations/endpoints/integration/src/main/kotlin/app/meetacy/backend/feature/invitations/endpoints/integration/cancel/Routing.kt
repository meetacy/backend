package app.meetacy.backend.feature.invitations.endpoints.integration.cancel

import app.meetacy.backend.feature.invitations.endpoints.cancel.CancelInvitationForm
import app.meetacy.backend.feature.invitations.endpoints.cancel.CancelInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.cancel.CancelInvitationResponse
import app.meetacy.backend.feature.invitations.endpoints.cancel.invitationCancel
import app.meetacy.backend.feature.invitations.usecase.cancel.CancelInvitationUsecase
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.invitation.type
import app.meetacy.di.global.di
import io.ktor.server.routing.*

fun Route.invitationCancel() {
    val usecase: CancelInvitationUsecase by di.getting
    val repository = object : CancelInvitationRepository {
        override suspend fun cancel(form: CancelInvitationForm): CancelInvitationResponse =
            usecase.cancel(form.token.type(), form.id.type()).toEndpoint()

        private fun CancelInvitationUsecase.Result.toEndpoint(): CancelInvitationResponse = when (this) {
            CancelInvitationUsecase.Result.NotFound -> CancelInvitationResponse.NotFound
            CancelInvitationUsecase.Result.Success -> CancelInvitationResponse.Success
            CancelInvitationUsecase.Result.Unauthorized -> CancelInvitationResponse.Unauthorized
        }
    }

    invitationCancel(repository)
}
