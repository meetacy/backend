package app.meetacy.backend.feature.invitations.usecase.integration.view

import app.meetacy.backend.feature.invitations.usecase.get.ViewInvitationsUsecase
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation
import app.meetacy.backend.feature.invitations.usecase.types.InvitationView
import app.meetacy.backend.feature.invitations.usecase.types.ViewInvitationsRepository
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.viewInvitation() {
    val viewInvitationUsecase by singleton<ViewInvitationsUsecase> {
        val usersRepository: GetUsersViewsRepository by getting
        val meetingsRepository: GetMeetingsViewsRepository by getting
        ViewInvitationsUsecase(usersRepository, meetingsRepository)
    }
    val viewInvitationRepository by singleton<ViewInvitationsRepository> {
        val usecase: ViewInvitationsUsecase by getting
        object : ViewInvitationsRepository {
            override suspend fun viewInvitations(
                viewerId: UserId,
                invitations: List<FullInvitation>
            ): List<InvitationView> {
                return usecase.viewInvitations(viewerId, invitations)
            }
        }
    }
}
