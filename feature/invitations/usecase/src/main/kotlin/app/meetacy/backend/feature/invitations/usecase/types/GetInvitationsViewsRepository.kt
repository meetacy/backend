package app.meetacy.backend.feature.invitations.usecase.types

import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.invitation.InvitationView
import app.meetacy.backend.types.users.UserId

interface GetInvitationsViewsRepository {
    suspend fun getInvitationsViewsOrNull(
        viewerId: UserId,
        invitationIds: List<InvitationId>
    ): List<InvitationView?>
}

suspend fun GetInvitationsViewsRepository.getInvitationViewOrNull(
    viewerId: UserId,
    invitationId: InvitationId
): InvitationView? {
    return getInvitationsViewsOrNull(
        viewerId = viewerId,
        invitationIds = listOf(invitationId)
    ).first()
}

suspend fun GetInvitationsViewsRepository.getInvitationView(
    viewerId: UserId,
    invitationId: InvitationId
): InvitationView {
    return getInvitationViewOrNull(viewerId, invitationId) ?: error("Cannot find invitation with id $viewerId")
}

suspend fun GetInvitationsViewsRepository.getInvitationsViews(
    viewerId: UserId,
    invitationIds: List<InvitationId>
): List<InvitationView> {
    return getInvitationsViewsOrNull(viewerId, invitationIds)
        .filterNotNull()
        .apply {
            require(size == invitationIds.size) { "Couldn't find all invitations $invitationIds" }
        }
}
