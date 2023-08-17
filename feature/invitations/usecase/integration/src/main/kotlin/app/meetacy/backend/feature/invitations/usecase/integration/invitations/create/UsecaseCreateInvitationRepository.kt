package app.meetacy.backend.feature.invitations.usecase.integration.invitations.create

import app.meetacy.backend.feature.invitations.endpoints.create.CreateInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.create.InvitationCreatingFormSerializable
import app.meetacy.backend.feature.invitations.endpoints.create.InvitationsCreateResponse
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.backend.feature.invitations.usecase.integration.types.toEndpoint
import app.meetacy.backend.feature.invitations.usecase.create.CreateInvitationUsecase

class UsecaseCreateInvitationRepository(
    private val usecase: CreateInvitationUsecase
): CreateInvitationRepository {
    override suspend fun createInvitation(form: InvitationCreatingFormSerializable): InvitationsCreateResponse {
        with(form) {
            val response = usecase.createInvitation(
                token = token.type(),
                meetingIdentity = meetingId.type()!!,
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
