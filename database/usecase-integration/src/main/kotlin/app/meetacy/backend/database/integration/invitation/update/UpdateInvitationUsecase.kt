package app.meetacy.backend.database.integration.invitation.update

import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.database.types.DatabaseInvitation
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.update.UpdateInvitationUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseUpdateInvitationStorage(db: Database): UpdateInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)
    private val meetingsTable = MeetingsTable(db)
    private val participantsTable = ParticipantsTable(db)

    override suspend fun InvitationId.doesExist(): Boolean =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(this))
            .singleOrNull() is DatabaseInvitation

    override suspend fun MeetingId.doesExist(): Boolean =
        meetingsTable
            .getMeetingOrNull(this) != null

    override suspend fun InvitationId.getInvitedUser(): UserId =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(this))
            .single()
            .invitedUserId

    override suspend fun MeetingId.ableToInvite(invitorId: UserId, invitedId: UserId): Boolean {
        val invitorIsParticipating = participantsTable
            .isParticipating(this, invitorId)

        val invitedIsParticipating = participantsTable
            .isParticipating(this, invitedId)

        return invitorIsParticipating && !invitedIsParticipating
        // TODO: Due to limited possibilities of MeetingsTable validation ends up with this
    }

    override suspend fun InvitationId.isCreatedBy(userId: UserId): Boolean =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(this))
            .single()
            .invitorUserId == userId

    override suspend fun InvitationId.update(
        title: String?,
        description: String?,
        expiryDate: Date?,
        meetingId: MeetingId?
    ): Boolean =
        invitationsTable
            .update(
                invitationId = this,
                title, description, expiryDate, meetingId
            )

}