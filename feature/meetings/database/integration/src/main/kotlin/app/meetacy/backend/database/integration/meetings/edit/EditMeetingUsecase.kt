package app.meetacy.backend.database.integration.meetings.edit

import app.meetacy.backend.database.integration.types.mapToDatabase
import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.meetings.MeetingsStorage
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.optional.Optional
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import org.jetbrains.exposed.sql.Database

class DatabaseEditMeetingStorage(db: Database) : EditMeetingUsecase.Storage {
    private val meetingsStorage = MeetingsStorage(db)

    override suspend fun editMeeting(
        meetingId: MeetingId,
        avatarId: Optional<FileId?>,
        title: String?,
        description: String?,
        location: Location?,
        date: Date?,
        visibility: FullMeeting.Visibility?
    ): FullMeeting {
         return meetingsStorage.editMeeting(
            meetingId,
            avatarId,
            title,
            description,
            location,
            date,
            visibility?.mapToDatabase()
        ).mapToUsecase()
    }
}
