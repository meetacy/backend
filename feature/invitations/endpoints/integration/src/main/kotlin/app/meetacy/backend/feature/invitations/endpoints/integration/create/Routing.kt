package app.meetacy.backend.feature.invitations.endpoints.integration.create

import app.meetacy.backend.feature.invitations.endpoints.create.CreateInvitationRepository
import app.meetacy.backend.feature.invitations.endpoints.create.InvitationsCreateResponse
import app.meetacy.backend.feature.invitations.endpoints.create.invitationCreate
import app.meetacy.backend.feature.invitations.usecase.create.CreateInvitationUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.invitation.serializable
import app.meetacy.backend.types.serializable.meetings.MeetingId
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.types.serializable.users.UserId
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.invitationCreate(di: DI) {
    val usecase: CreateInvitationUsecase by di.getting
    val repository = object : CreateInvitationRepository {
        override suspend fun createInvitation(token: AccessIdentity, meetingId: MeetingId, userId: UserId): InvitationsCreateResponse {
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
                    InvitationsCreateResponse.Success(response.invitation.serializable())
            }
        }
    }
    invitationCreate(repository)
}
