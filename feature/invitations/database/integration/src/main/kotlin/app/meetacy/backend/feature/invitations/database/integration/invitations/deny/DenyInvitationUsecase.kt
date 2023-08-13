package app.meetacy.backend.feature.invitations.database.integration.invitations.deny

import app.meetacy.backend.feature.invitations.database.integration.types.mapToUsecase
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsStorage
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.feature.invitations.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation
import org.jetbrains.exposed.sql.Database

class DatabaseDenyInvitationStorage(db: Database): DenyInvitationUsecase.Storage {
    private val invitationsStorage = InvitationsStorage(db)

    override suspend fun getInvitation(id: InvitationId): FullInvitation? =
        invitationsStorage.getInvitationsOrNull(listOf(id)).singleOrNull()?.mapToUsecase()

    override suspend fun markAsDenied(id: InvitationId): Boolean =
        invitationsStorage.markAsDenied(id)
}
