package app.meetacy.backend.database.integration.meetings.participate

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseParticipateMeetingStorage(db: Database) : ParticipateMeetingUsecase.Storage {
    private val participantsTable = ParticipantsTable(db)
    override suspend fun addParticipant(meetingId: MeetingId, userId: UserId) {
        participantsTable.addParticipant(meetingId, userId)
    }

    override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        participantsTable.isParticipating(meetingId, userId)
}