package app.meetacy.backend.feature.invitations.usecase.get

import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.invitation.FullInvitation
import app.meetacy.backend.types.invitation.InvitationView
import app.meetacy.backend.feature.invitations.usecase.types.ViewInvitationsRepository

class GetInvitationsViewsUsecase(
    private val viewInvitationsRepository: ViewInvitationsRepository,
    private val invitationsProvider: InvitationsProvider
) {
    suspend fun getInvitationsViewsOrNull(
        viewerId: UserId,
        invitationIds: List<InvitationId>
    ): List<InvitationView?> {
        val invitations = invitationsProvider.getInvitationsOrNull(invitationIds)

        val views = viewInvitationsRepository.viewInvitations(
            viewerId = viewerId,
            invitations = invitations.filterNotNull()
        ).iterator()

        return invitations.map { invitation ->
            if (invitation == null) return@map null
            views.next()
        }
    }

    interface InvitationsProvider {
        suspend fun getInvitationsOrNull(invitationIds: List<InvitationId>): List<FullInvitation?>
    }
}
