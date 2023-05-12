package app.meetacy.backend.database.integration.invitation.read

import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.types.DatabaseInvitation
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.read.ReadInvitationUsecase
import app.meetacy.backend.usecase.types.Invitation
import org.jetbrains.exposed.sql.Database


class DatabaseReadInvitationStorage(db: Database): ReadInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)
    private val usersTable = UsersTable(db)

    override suspend fun UserId.getInvitations(): List<Invitation> {
        val invitations = invitationsTable.getInvitations(invitedUserId = this)
        return invitations.map { it.toInvitation() }
    }

    override suspend fun UserId.getInvitations(from: List<UserId>): List<Invitation> {
        val invitations = invitationsTable
            .getInvitations(invitedUserId = this, invitorUserIdsList = from)
            .filter { it.invitorUserId in from }

        return invitations.map { it.toInvitation() }
    }

    override suspend fun UserId.getInvitationsByIds(ids: List<InvitationId>): List<Invitation> {
        val invitations = invitationsTable
            .getInvitationsByInvitationIds(this, ids)

        return invitations.map { it.toInvitation() }
    }

    override suspend fun UserId.doesExist(): Boolean =
        usersTable
            .getUsersOrNull(listOf(this))
            .singleOrNull() != null

    override suspend fun InvitationId.doesExist(): Boolean =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(this))
            .singleOrNull() != null


    private fun DatabaseInvitation.toInvitation() = Invitation(
        identity,
        expiryDate,
        invitedUserId,
        invitorUserId,
        meeting,
        title,
        description
    )
}
