package app.meetacy.backend.usecase.integration.invitations.create

import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.endpoint.invitations.create.InvitationCreatingFormSerializable
import app.meetacy.backend.endpoint.invitations.create.InvitationsCreateResponse
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.usecase.integration.types.toEndpoint
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase

class UsecaseCreateInvitationRepository(
    private val usecase: CreateInvitationUsecase
): CreateInvitationRepository {
    override suspend fun createInvitation(form: InvitationCreatingFormSerializable): InvitationsCreateResponse {
        with(form) {
            val response = usecase.createInvitation(
                token = token.type(),
                meetingIdentity = meetingId.type(),
                userIdentity = userId.type()
            )
            return when (response) {
                CreateInvitationUsecase.Result.UserAlreadyInvited ->
                    InvitationsCreateResponse.UserAlreadyInvited
                CreateInvitationUsecase.Result.UserNotFound ->
                    InvitationsCreateResponse.UserNotFound
                CreateInvitationUsecase.Result.NoPermissions ->
                    InvitationsCreateResponse.NoPermissions
                CreateInvitationUsecase.Result.MeetingNotFound ->
                    InvitationsCreateResponse.MeetingNotFound
                CreateInvitationUsecase.Result.Unauthorized ->
                    InvitationsCreateResponse.Unauthorized
                is CreateInvitationUsecase.Result.Success ->
                    InvitationsCreateResponse.Success(response.invitation.toEndpoint())
            }
        }
    }
}
