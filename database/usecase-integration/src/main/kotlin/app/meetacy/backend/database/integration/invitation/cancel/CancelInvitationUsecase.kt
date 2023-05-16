package app.meetacy.backend.database.integration.invitation.cancel

import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseCancelInvitationStorage(db: Database): CancelInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)

    override suspend fun isInvitor(userId: UserId, invitationId: InvitationId): Boolean =
        invitationsTable.getInvitationsByInvitationIds(listOf(invitationId)).singleOrNull()!!.invitorUserId == userId

    override suspend fun doesExist(id: InvitationId): Boolean {
        val invitation = invitationsTable
            .getInvitationsByInvitationIds(listOf(id))
            .singleOrNull()

        return invitation != null && invitation.isAccepted == null
    }

    override suspend fun isExpired(id: InvitationId): Boolean {
        val invitation = invitationsTable
            .getInvitationsByInvitationIds(listOf(id))
            .singleOrNull()

        return invitation!!.expiryDate < DateTime.now()
    }

    override suspend fun cancel(id: InvitationId): Boolean =
        invitationsTable.cancel(id)
}
