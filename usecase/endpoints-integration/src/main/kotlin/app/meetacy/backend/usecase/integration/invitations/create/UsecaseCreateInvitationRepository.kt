package app.meetacy.backend.usecase.integration.invitations.create

import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.endpoint.invitations.create.InvitationCreatingFormSerializable
import app.meetacy.backend.endpoint.invitations.create.InvitationsCreateResponse
import app.meetacy.backend.types.serialization.invitation.serializable
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase

class UsecaseCreateInvitationRepository(
    private val usecase: CreateInvitationUsecase
): CreateInvitationRepository {
    override suspend fun createInvitation(invitationCreatingForm: InvitationCreatingFormSerializable): InvitationsCreateResponse {
        with(invitationCreatingForm) {
            val response = usecase.createInvitation(
                token = token.type(),
                title = title ?: "",
                description = description ?: "",
                invitedUserId = invitedUser.type(),
                expiryDate = expiryDate.type().date,
                meetingId = meeting.type()
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
                    InvitationsCreateResponse.Success(response.invitation.serializable())

                CreateInvitationUsecase.Result.InvalidData ->
                    InvitationsCreateResponse.InvalidData
            }
        }
    }

}