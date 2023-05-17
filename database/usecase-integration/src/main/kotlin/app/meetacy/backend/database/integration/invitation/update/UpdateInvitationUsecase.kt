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

    override suspend fun doesExist(invitationId: InvitationId): Boolean =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(invitationId))
            .singleOrNull() is DatabaseInvitation

    override suspend fun doesExist(meetingId: MeetingId): Boolean =
        meetingsTable
            .getMeetingOrNull(meetingId) != null

    override suspend fun getInvitedUser(invitationId: InvitationId): UserId =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(invitationId))
            .single()
            .invitedUserId

    override suspend fun ableToInvite(meetingId: MeetingId, invitorId: UserId, invitedId: UserId): Boolean {
        val invitorIsParticipating = participantsTable
            .isParticipating(meetingId, invitorId)

        val invitedIsParticipating = participantsTable
            .isParticipating(meetingId, invitedId)

        return invitorIsParticipating && !invitedIsParticipating
        // TODO: Due to limited possibilities of MeetingsTable validation ends up with this
    }

    override suspend fun isCreatedBy(userId: UserId, invitationId: InvitationId): Boolean =
        invitationsTable
            .getInvitationsByInvitationIds(listOf(invitationId))
            .single()
            .invitorUserId == userId

    override suspend fun update(
        invitationId: InvitationId,
        title: String?,
        description: String?,
        expiryDate: Date?,
        meetingId: MeetingId?
    ): Boolean =
        invitationsTable
            .update(
                invitationId = invitationId,
                title, description, expiryDate, meetingId
            )

}