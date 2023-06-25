package app.meetacy.backend.database.integration.invitation.deny

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.invitations.InvitationsStorage
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.types.FullInvitation
import org.jetbrains.exposed.sql.Database

class DatabaseDenyInvitationStorage(db: Database): DenyInvitationUsecase.Storage {
    private val invitationsStorage = InvitationsStorage(db)

    override suspend fun getInvitation(id: InvitationId): FullInvitation? =
        invitationsStorage.getInvitationsByInvitationIds(listOf(id)).singleOrNull()?.mapToUsecase()

    override suspend fun markAsDenied(id: InvitationId): Boolean =
        invitationsStorage.markAsDenied(id)
}
