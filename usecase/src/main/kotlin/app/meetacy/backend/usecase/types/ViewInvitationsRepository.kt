package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.user.UserId

interface ViewInvitationsRepository {
    suspend fun viewInvitations(
        viewerId: UserId,
        invitations: List<FullInvitation>
    ): List<InvitationView>
}
