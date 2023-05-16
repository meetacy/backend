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

    override suspend fun getInvitations(invited: UserId): List<Invitation> {
        val invitations = invitationsTable.getInvitations(invitedUserId = invited)
        return invitations.map { it.toInvitation() }
    }

    override suspend fun getInvitations(from: List<UserId>, to: UserId): List<Invitation> {
        val invitations = invitationsTable
            .getInvitations(invitedUserId = to, invitorUserIdsList = from)
            .filter { it.invitorUserId in from }

        return invitations.map { it.toInvitation() }
    }

    override suspend fun getInvitationsByIds(ids: List<InvitationId>): List<Invitation> {
        return invitationsTable
            .getInvitationsByInvitationIds(ids)
            .map { it.toInvitation() }
    }

    override suspend fun doesExist(id: UserId): Boolean =
        usersTable
            .getUsersOrNull(listOf(id))
            .singleOrNull() != null

    override suspend fun doesExist(id: InvitationId): Boolean =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(id))
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
