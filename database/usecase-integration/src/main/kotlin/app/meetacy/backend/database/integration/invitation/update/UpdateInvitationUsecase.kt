package app.meetacy.backend.database.integration.invitation.update

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.integration.types.toUsecase
import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.update.UpdateInvitationUsecase
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.FullMeeting
import org.jetbrains.exposed.sql.Database

class DatabaseUpdateInvitationStorage(db: Database): UpdateInvitationUsecase.Storage {
    private val invitationsTable = InvitationsTable(db)
    private val meetingsTable = MeetingsTable(db)
    private val participantsTable = ParticipantsTable(db)

    override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        participantsTable.isParticipating(meetingId, userId)

    override suspend fun getInvitationOrNull(id: InvitationId): FullInvitation? =
        invitationsTable.getInvitationsByInvitationIds(listOf(id)).singleOrNull()?.toUsecase()

    override suspend fun update(
        invitationId: InvitationId,
        expiryDate: DateTime?,
        meetingId: MeetingId?
    ): Boolean =
        invitationsTable.update(invitationId, expiryDate, meetingId)

    override suspend fun getMeetingOrNull(id: MeetingId): FullMeeting? =
        meetingsTable.getMeetingOrNull(id)?.mapToUsecase()

}