package app.meetacy.backend.usecase.integration.invitations.update

import app.meetacy.backend.endpoint.invitations.update.InvitationUpdateRepository
import app.meetacy.backend.endpoint.invitations.update.InvitationUpdatingFormSerializable
import app.meetacy.backend.endpoint.invitations.update.InvitationsUpdateResponse
import app.meetacy.backend.usecase.invitations.update.UpdateInvitationUsecase

class UsecaseUpdateInvitationRepository(
    private val usecase: UpdateInvitationUsecase
): InvitationUpdateRepository {
    override suspend fun update(form: InvitationUpdatingFormSerializable): InvitationsUpdateResponse =
        with(usecase) {
            form.id.type().update(
                token = form.token.type(),
                title = form.title,
                description = form.description,
                meetingId = form.meetingId.type(),
                expiryDate = form.expiryDate?.type()
            ).toEndpoint()
        }

    private fun UpdateInvitationUsecase.Result.toEndpoint() = when (this) {
        UpdateInvitationUsecase.Result.InvalidData -> InvitationsUpdateResponse.InvalidData
        UpdateInvitationUsecase.Result.InvitationNotFound -> InvitationsUpdateResponse.InvitationNotFound
        UpdateInvitationUsecase.Result.MeetingNotFound -> InvitationsUpdateResponse.MeetingNotFound
        UpdateInvitationUsecase.Result.Success -> InvitationsUpdateResponse.Success
        UpdateInvitationUsecase.Result.Unauthorized -> InvitationsUpdateResponse.Unauthorized
    }
}