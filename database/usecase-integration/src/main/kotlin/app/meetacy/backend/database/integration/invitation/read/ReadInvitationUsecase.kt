package app.meetacy.backend.database.integration.invitation.read

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.invitations.InvitationsStorage
import app.meetacy.backend.database.users.UsersStorage
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.read.ReadInvitationUsecase
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.FullUser
import org.jetbrains.exposed.sql.Database


class DatabaseReadInvitationStorage(db: Database): ReadInvitationUsecase.Storage {
    private val invitationsStorage = InvitationsStorage(db)
    private val usersStorage = UsersStorage(db)

    override suspend fun getInvitations(invited: UserId): List<FullInvitation> {
        val invitations = invitationsStorage.getInvitations(userIds = listOf(invited))
            .filter { it.invitedUserId == invited }
        return invitations.map { it.mapToUsecase() }
    }

    override suspend fun getInvitations(from: List<UserId>, to: UserId): List<FullInvitation> {
        val invitations = invitationsStorage
            .getInvitations(userIds = from + to)
            .filter { it.invitorUserId in from && it.invitedUserId == to }

        return invitations.map { it.mapToUsecase() }
    }

    override suspend fun getInvitationsByIds(ids: List<InvitationId>): List<FullInvitation> {
        return invitationsStorage
            .getInvitationsByInvitationIds(ids)
            .map { it.mapToUsecase() }
    }

    override suspend fun getFullUser(id: UserId): FullUser? =
        usersStorage.getUsersOrNull(listOf(id)).singleOrNull()?.mapToUsecase()

    override suspend fun getInvitation(id: InvitationId): FullInvitation? =
        invitationsStorage
            .getInvitationsByInvitationIds(listOf(id))
            .singleOrNull()?.mapToUsecase()
}
