package app.meetacy.backend.database.integration.invitation.deny

import app.meetacy.backend.database.integration.types.toUsecase
import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.types.FullInvitation
import org.jetbrains.exposed.sql.Database

class DatabaseDenyInvitationStorage(db: Database): DenyInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)

    override suspend fun getInvitation(id: InvitationId): FullInvitation? =
        invitationsTable.getInvitationsByInvitationIds(listOf(id)).singleOrNull()?.toUsecase()

    override suspend fun markAsDenied(id: InvitationId): Boolean =
        invitationsTable.markAsDenied(id)
}
