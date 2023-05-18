package app.meetacy.backend.database.integration.invitation.accept

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.integration.types.toUsecase
import app.meetacy.backend.database.invitations.InvitationsTable
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase.Storage
import app.meetacy.backend.usecase.types.FullInvitation
import app.meetacy.backend.usecase.types.FullMeeting
import org.jetbrains.exposed.sql.Database

class DatabaseAcceptInvitationStorage(db: Database): Storage {
    private val invitationsTable = InvitationsTable(db)
    private val participantsTable = ParticipantsTable(db)
    private val meetingsTable = MeetingsTable(db)

    override suspend fun getMeetingOrNull(id: MeetingId): FullMeeting? =
        meetingsTable.getMeetingOrNull(id)?.mapToUsecase()

    override suspend fun getInvitationOrNull(id: InvitationId): FullInvitation? =
        invitationsTable.getInvitationsByInvitationIds(listOf(id)).singleOrNull()?.toUsecase()

    override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        participantsTable.isParticipating(meetingId, userId)

    override suspend fun markAsAccepted(id: InvitationId) {
        invitationsTable.markAsAccepted(id)
    }

    override suspend fun addToMeeting(id: MeetingId, userId: UserId) {
        participantsTable.addParticipant(userId, id)
    }
}