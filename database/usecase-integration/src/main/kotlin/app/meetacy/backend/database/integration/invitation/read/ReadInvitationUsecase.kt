package app.meetacy.backend.database.integration.invitation.read

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.integration.types.toUsecase
import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.read.ReadInvitationUsecase
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.FullInvitation
import org.jetbrains.exposed.sql.Database


class DatabaseReadInvitationStorage(db: Database): ReadInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)
    private val usersTable = UsersTable(db)

    override suspend fun getInvitations(invited: UserId): List<FullInvitation> {
        val invitations = invitationsTable.getInvitations(invitedUserId = invited)
        return invitations.map { it.toUsecase() }
    }

    override suspend fun getInvitations(from: List<UserId>, to: UserId): List<FullInvitation> {
        val invitations = invitationsTable
            .getInvitations(invitedUserId = to, invitorUserIdsList = from)
            .filter { it.invitorUserId in from }

        return invitations.map { it.toUsecase() }
    }

    override suspend fun getInvitationsByIds(ids: List<InvitationId>): List<FullInvitation> {
        return invitationsTable
            .getInvitationsByInvitationIds(ids)
            .map { it.toUsecase() }
    }

    override suspend fun getFullUser(id: UserId): FullUser? =
        usersTable.getUsersOrNull(listOf(id)).singleOrNull()?.mapToUsecase()

    override suspend fun getInvitation(id: InvitationId): FullInvitation? =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(id))
            .singleOrNull()?.toUsecase()
}
