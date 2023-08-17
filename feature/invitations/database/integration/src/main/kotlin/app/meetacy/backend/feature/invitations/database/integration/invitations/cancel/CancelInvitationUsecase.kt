package app.meetacy.backend.feature.invitations.database.integration.invitations.cancel

import app.meetacy.backend.feature.invitations.database.integration.types.mapToUsecase
import app.meetacy.backend.feature.invitations.database.invitations.InvitationsStorage
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.feature.invitations.usecase.cancel.CancelInvitationUsecase
import app.meetacy.backend.feature.invitations.usecase.types.FullInvitation
import org.jetbrains.exposed.sql.Database

class DatabaseCancelInvitationStorage(db: Database): CancelInvitationUsecase.Storage {
    private val invitationsStorage = InvitationsStorage(db)

    override suspend fun cancel(id: InvitationId): Boolean =
        invitationsStorage.cancel(id)

    override suspend fun getInvitation(id: InvitationId): FullInvitation? =
        invitationsStorage.getInvitationsOrNull(listOf(id)).singleOrNull()?.mapToUsecase()
}
