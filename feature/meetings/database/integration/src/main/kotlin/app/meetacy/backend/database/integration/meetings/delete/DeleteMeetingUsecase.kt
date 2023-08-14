package app.meetacy.backend.database.integration.meetings.delete

import app.meetacy.backend.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.auth.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.backend.types.meeting.MeetingId
import org.jetbrains.exposed.sql.Database

class DatabaseDeleteMeetingStorage(db: Database) : DeleteMeetingUsecase.Storage {
    private val meetingsStorage = MeetingsStorage(db)

    override suspend fun deleteMeeting(meetingId: MeetingId) {
        meetingsStorage.deleteMeeting(meetingId)
    }
}