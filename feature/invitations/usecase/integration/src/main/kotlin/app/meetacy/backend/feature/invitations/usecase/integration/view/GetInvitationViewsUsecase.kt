package app.meetacy.backend.feature.invitations.usecase.integration.view

import app.meetacy.backend.feature.invitations.database.invitations.InvitationsStorage
import app.meetacy.backend.feature.invitations.usecase.get.GetInvitationsViewsUsecase
import app.meetacy.backend.feature.invitations.usecase.integration.types.mapToUsecase
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation
import app.meetacy.backend.feature.invitations.usecase.types.GetInvitationsViewsRepository
import app.meetacy.backend.feature.invitations.usecase.types.InvitationView
import app.meetacy.backend.feature.invitations.usecase.types.ViewInvitationsRepository
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.getInvitationViews() {
    val provider by singleton<GetInvitationsViewsUsecase.InvitationsProvider> {
        object : GetInvitationsViewsUsecase.InvitationsProvider {
            private val invitationsStorage: InvitationsStorage by getting

            override suspend fun getInvitationsOrNull(invitationIds: List<InvitationId>): List<FullInvitation?> {
                return invitationsStorage
                    .getInvitationsOrNull(invitationIds)
                    .map { it?.mapToUsecase() }
            }
        }
    }
    val getInvitationViewsUsecase by singleton<GetInvitationsViewsUsecase> {
        val repository: ViewInvitationsRepository by getting
        val invitationsProvider: GetInvitationsViewsUsecase.InvitationsProvider by getting

        GetInvitationsViewsUsecase(repository, invitationsProvider)
    }
    val getInvitationsViewsRepository by singleton<GetInvitationsViewsRepository> {
        val usecase: GetInvitationsViewsUsecase by getting
        object : GetInvitationsViewsRepository {
            override suspend fun getInvitationsViewsOrNull(
                viewerId: UserId,
                invitationIds: List<InvitationId>
            ): List<InvitationView?> {
                return usecase.getInvitationsViewsOrNull(viewerId, invitationIds)
            }
        }
    }
}
