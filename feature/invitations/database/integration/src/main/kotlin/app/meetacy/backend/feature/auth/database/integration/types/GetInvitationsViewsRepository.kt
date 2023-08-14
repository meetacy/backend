package app.meetacy.backend.feature.auth.database.integration.types

import app.meetacy.backend.feature.auth.usecase.invitations.get.GetInvitationsViewsUsecase
import app.meetacy.backend.feature.auth.usecase.types.GetInvitationsViewsRepository
import app.meetacy.backend.feature.auth.usecase.types.InvitationView
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId

class UsecaseGetInvitationsViewsRepository(
    val usecase: GetInvitationsViewsUsecase
) : GetInvitationsViewsRepository {
    override suspend fun getInvitationsViewsOrNull(
        viewerId: UserId,
        invitationIds: List<InvitationId>
    ): List<InvitationView?> {
        return usecase.getInvitationsViewsOrNull(viewerId, invitationIds)
    }
}
