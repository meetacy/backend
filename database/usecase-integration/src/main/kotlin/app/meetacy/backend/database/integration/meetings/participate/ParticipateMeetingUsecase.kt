package app.meetacy.backend.database.integration.meetings.participate

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseParticipateMeetingStorage(db: Database) : ParticipateMeetingUsecase.Storage {
    private val participantsTable = ParticipantsTable(db)
    override suspend fun addParticipant(participantId: UserId, meetingId: MeetingId) {
        participantsTable.addParticipant(participantId, meetingId)
    }

    override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        participantsTable.isParticipating(meetingId, userId)
}