package app.meetacy.backend.feature.invitations.database.integration.types

import app.meetacy.backend.feature.invitations.usecase.types.ViewInvitationsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.invitations.usecase.invitations.get.ViewInvitationsUsecase
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation
import app.meetacy.backend.feature.invitations.usecase.types.InvitationView

class UsecaseViewInvitationsRepository(
    private val usecase: ViewInvitationsUsecase
) : ViewInvitationsRepository {
    override suspend fun viewInvitations(
        viewerId: UserId,
        invitations: List<FullInvitation>
    ): List<InvitationView> {
        return usecase.viewInvitations(viewerId, invitations)
    }
}
