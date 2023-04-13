package app.meetacy.backend.database.integration.meetings.avatar.delete

import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.usecase.meetings.avatar.delete.DeleteMeetingAvatarUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseDeleteMeetingAvatarStorage(db: Database) : DeleteMeetingAvatarUsecase.Storage {
    private val meetingsTable = MeetingsTable(db)

    override suspend fun deleteAvatar(idMeeting: IdMeeting) {
        meetingsTable.deleteAvatar(idMeeting)
    }
}