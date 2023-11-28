package app.meetacy.backend.feature.invitations.usecase.types

import app.meetacy.backend.types.invitation.FullInvitation
import app.meetacy.backend.types.invitation.InvitationView
import app.meetacy.backend.types.users.UserId

interface ViewInvitationsRepository {
    suspend fun viewInvitations(
        viewerId: UserId,
        invitations: List<FullInvitation>
    ): List<InvitationView>
}
