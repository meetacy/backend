package app.meetacy.backend.database.integration.invitation.deny

import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseDenyInvitationStorage(db: Database): DenyInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)
    private val usersTable = UsersTable(db)
    override suspend fun UserId.isInvited(invitation: InvitationId): Boolean =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(invitation))
            .singleOrNull()
            ?.invitedUserId == this

    override suspend fun UserId.doesExist(): Boolean =
        usersTable.getUsersOrNull(listOf(this)).singleOrNull() != null

    override suspend fun InvitationId.doesExist(): Boolean {
        val invitation = invitationsTable
            .getInvitationsByInvitationIds(listOf(this))
            .singleOrNull()

        return invitation != null && invitation.isAccepted == null
    }

    override suspend fun InvitationId.markAsDenied(): Boolean =
        invitationsTable.markAsDenied(this)

    override suspend fun InvitationId.isExpired(): Boolean {
        val invitation = invitationsTable
            .getInvitationsByInvitationIds(listOf(this))
            .singleOrNull() ?: return true

        return invitation.expiryDate > Date.today()
    }

}
