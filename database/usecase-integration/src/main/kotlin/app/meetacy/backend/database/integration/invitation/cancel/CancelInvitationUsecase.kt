package app.meetacy.backend.database.integration.invitation.cancel

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.usecase.types.FullInvitation
import org.jetbrains.exposed.sql.Database

class DatabaseCancelInvitationStorage(db: Database): CancelInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)

    override suspend fun cancel(id: InvitationId): Boolean =
        invitationsTable.cancel(id)

    override suspend fun getInvitation(id: InvitationId): FullInvitation? =
        invitationsTable.getInvitationsByInvitationIds(listOf(id)).singleOrNull()?.mapToUsecase()
}
