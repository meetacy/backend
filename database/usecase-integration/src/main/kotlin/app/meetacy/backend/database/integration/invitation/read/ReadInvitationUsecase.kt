package app.meetacy.backend.database.integration.invitation.read

import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.types.DatabaseInvitation
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.read.ReadInvitationUsecase
import app.meetacy.backend.usecase.invitations.read.ReadInvitationUsecase.Storage.DatabaseResult
import app.meetacy.backend.usecase.types.Invitation
import org.jetbrains.exposed.sql.Database


class DatabaseReadInvitationStorage(db: Database): ReadInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)

    override suspend fun UserId.getInvitations(): DatabaseResult {
        val invitations = invitationsTable.getInvitations(invitedUserId = this)
        return DatabaseResult.Success(invitations.map { it.toInvitation() })
    }

    override suspend fun UserId.getInvitations(from: List<UserId>): DatabaseResult {
        val invitations = invitationsTable
            .getInvitations(invitedUserId = this, invitorUserIdsList = from)
            .filter { it.invitorUserId in from }

        val retrievedFrom = invitations.map { it.invitorUserId }.toSet()

        return if (retrievedFrom != from.toSet()) {
            DatabaseResult.UsersNotFound
        } else {
            DatabaseResult.Success(invitations.map { it.toInvitation() })
        }
    }

    override suspend fun UserId.getInvitationsByIds(ids: List<InvitationId>): DatabaseResult {
        val invitations = invitationsTable
            .getInvitationsByInvitationIds(this, ids)

        return if (invitations.map { it.id }.toSet() != ids) {
            DatabaseResult.InvitationsNotFound
        } else {
            DatabaseResult.Success(invitations.map { it.toInvitation() })
        }
    }

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
