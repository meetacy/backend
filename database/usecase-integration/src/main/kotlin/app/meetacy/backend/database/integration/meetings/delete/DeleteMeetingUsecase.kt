package app.meetacy.backend.database.integration.meetings.delete

import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseDeleteMeetingStorage(db: Database) : DeleteMeetingUsecase.Storage {
    private val meetingsTable = MeetingsTable(db)

    override suspend fun deleteMeeting(idMeeting: IdMeeting) {
        meetingsTable.deleteMeeting(idMeeting)
    }
}