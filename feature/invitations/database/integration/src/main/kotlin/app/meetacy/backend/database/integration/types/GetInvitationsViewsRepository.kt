package app.meetacy.backend.database.integration.types

import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.usecase.invitations.get.GetInvitationsViewsUsecase
import app.meetacy.backend.usecase.types.GetInvitationsViewsRepository
import app.meetacy.backend.usecase.types.InvitationView

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
