package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.user.UserId

interface GetInvitationsViewsRepository {
    suspend fun getInvitationView(viewerId: UserId, invitation: FullInvitation): InvitationView?
}