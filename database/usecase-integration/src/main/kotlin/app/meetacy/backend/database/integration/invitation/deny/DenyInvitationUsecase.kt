package app.meetacy.backend.database.integration.invitation.deny

import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseDenyInvitationStorage(db: Database): DenyInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)
    override suspend fun isInvited(invitation: InvitationId, user: UserId): Boolean =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(invitation))
            .singleOrNull()
            ?.invitedUserId == user

    override suspend fun doesExist(id: InvitationId): Boolean {
        val invitation = invitationsTable
            .getInvitationsByInvitationIds(listOf(id))
            .singleOrNull()

        return invitation != null && invitation.isAccepted == null
    }

    override suspend fun markAsDenied(id: InvitationId): Boolean =
        invitationsTable.markAsDenied(id)

    override suspend fun isExpired(id: InvitationId): Boolean {
        val invitation = invitationsTable
            .getInvitationsByInvitationIds(listOf(id))
            .singleOrNull() ?: return true

        return invitation.expiryDate > DateTime.now()
    }

}
