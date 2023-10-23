package app.meetacy.backend.feature.invitations.endpoints.integration.accept

import app.meetacy.backend.feature.invitations.endpoints.accept.AcceptInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.accept.InvitationAcceptResponse
import app.meetacy.backend.feature.invitations.endpoints.accept.invitationAccept
import app.meetacy.backend.feature.invitations.usecase.accept.AcceptInvitationUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.invitation.InvitationId
import app.meetacy.backend.types.serializable.invitation.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.invitationAccept(di: DI) {
    val usecase: AcceptInvitationUsecase by di.getting
    val repository = object : AcceptInvitationRepository {
        override suspend fun acceptInvitation(token: AccessIdentity, invitationId: InvitationId): InvitationAcceptResponse =
            usecase.accept(token.type(), invitationId.type()).toEndpoint()


        private fun AcceptInvitationUsecase.Result.toEndpoint(): InvitationAcceptResponse = when (this) {
            AcceptInvitationUsecase.Result.NotFound -> InvitationAcceptResponse.NotFound
            AcceptInvitationUsecase.Result.Success -> InvitationAcceptResponse.Success
            AcceptInvitationUsecase.Result.Unauthorized -> InvitationAcceptResponse.Unauthorized
            AcceptInvitationUsecase.Result.MeetingNotFound -> InvitationAcceptResponse.MeetingNotFound
        }

    }
    invitationAccept(repository)
}
