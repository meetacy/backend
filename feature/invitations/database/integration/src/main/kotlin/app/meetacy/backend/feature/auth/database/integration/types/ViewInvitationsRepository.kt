package app.meetacy.backend.feature.auth.database.integration.types

import app.meetacy.backend.feature.auth.usecase.invitations.get.ViewInvitationsUsecase
import app.meetacy.backend.feature.auth.usecase.types.FullInvitation
import app.meetacy.backend.feature.auth.usecase.types.InvitationView
import app.meetacy.backend.feature.auth.usecase.types.ViewInvitationsRepository
import app.meetacy.backend.types.user.UserId

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
