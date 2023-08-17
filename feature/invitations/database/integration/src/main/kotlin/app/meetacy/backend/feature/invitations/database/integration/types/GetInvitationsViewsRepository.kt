package app.meetacy.backend.feature.invitations.database.integration.types

import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.invitations.usecase.get.GetInvitationsViewsUsecase
import app.meetacy.backend.feature.invitations.usecase.types.GetInvitationsViewsRepository
import app.meetacy.backend.feature.invitations.usecase.types.InvitationView

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
