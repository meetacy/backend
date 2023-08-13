package app.meetacy.feature.meetings.database.integration.meetings.delete

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.feature.meetings.usecase.delete.DeleteMeetingUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseDeleteMeetingStorage(db: Database) : DeleteMeetingUsecase.Storage {
    private val meetingsStorage = MeetingsStorage(db)

    override suspend fun deleteMeeting(meetingId: MeetingId) {
        meetingsStorage.deleteMeeting(meetingId)
    }
}