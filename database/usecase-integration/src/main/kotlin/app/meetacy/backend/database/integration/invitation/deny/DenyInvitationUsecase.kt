package app.meetacy.backend.database.integration.invitation.deny

import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseDenyInvitationStorage(db: Database): DenyInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)
    override suspend fun UserId.isInvited(invitation: InvitationId): Boolean =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(invitation))
            .singleOrNull()
            ?.invitedUserId == this

    override suspend fun InvitationId.doesExist(): Boolean =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(this))
            .singleOrNull() != null

    override suspend fun InvitationId.markAsDenied(): Boolean =
        invitationsTable.markAsDenied(this)

}
