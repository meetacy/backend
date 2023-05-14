package app.meetacy.backend.database.integration.invitation.cancel

import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseCancelInvitationStorage(db: Database): CancelInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)
    private val usersTable = UsersTable(db)

    override suspend fun UserId.isInvitor(invitationId: InvitationId): Boolean =
        invitationsTable.getInvitationsByInvitationIds(listOf(invitationId)).singleOrNull()!!.invitorUserId == this

    override suspend fun InvitationId.doesExist(): Boolean {
        val invitation = invitationsTable
            .getInvitationsByInvitationIds(listOf(this))
            .singleOrNull()

        return invitation != null && invitation.isAccepted == null
    }

    override suspend fun InvitationId.isExpired(): Boolean {
        val invitation = invitationsTable
            .getInvitationsByInvitationIds(listOf(this))
            .singleOrNull()

        return invitation!!.expiryDate < DateTime.now()
    }

    override suspend fun UserId.doesExist(): Boolean {
        return usersTable.getUsersOrNull(listOf(this)).singleOrNull() != null
    }

    override suspend fun InvitationId.cancel(): Boolean =
        invitationsTable.cancel(this)

}