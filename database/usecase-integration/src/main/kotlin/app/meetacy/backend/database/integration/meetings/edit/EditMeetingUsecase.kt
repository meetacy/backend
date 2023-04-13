package app.meetacy.backend.database.integration.meetings.edit

import app.meetacy.backend.database.integration.types.mapToDatabase
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import org.jetbrains.exposed.sql.Database

class DatabaseEditMeetingStorage(db: Database) : EditMeetingUsecase.Storage {
    private val meetingsTable = MeetingsTable(db)

    override suspend fun editMeeting(
        meetingId: MeetingId,
        avatarId: FileIdentity?,
        deleteAvatar: Boolean,
        title: String?,
        description: String?,
        location: Location?,
        date: Date?,
        visibility: FullMeeting.Visibility?
    ) {
        meetingsTable.editMeeting(
            meetingId,
            avatarId,
            deleteAvatar,
            title,
            description,
            location,
            date,
            visibility?.mapToDatabase()
        )
    }
}