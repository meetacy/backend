package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId

interface GetInvitationsViewsRepository {
    suspend fun getInvitationViewOrNull(viewerId: UserId, invitation: FullInvitation): InvitationView?
    suspend fun getInvitationViewOrNull(viewerId: UserId, invitation: InvitationId): InvitationView?
    suspend fun getInvitationView(viewerId: UserId, invitation: FullInvitation): InvitationView
    suspend fun getInvitationView(viewerId: UserId, invitation: InvitationId): InvitationView
}