package app.meetacy.backend.database.integration

import app.meetacy.backend.database.MeetingsTable
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.Date
import app.meetacy.backend.types.Location
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.meetings.CreateMeetingUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import org.jetbrains.exposed.sql.Database

class DatabaseCreateMeetingStorage(db: Database) : CreateMeetingUsecase.Storage {
    private val meetingsTable = MeetingsTable(db)

    override suspend fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?
    ): FullMeeting {
        val meetingId = meetingsTable.addMeeting(accessHash, creatorId, date, location, title, description)
        return FullMeeting(
            id = meetingId,
            accessHash = accessHash,
            creatorId = creatorId,
            date = date,
            location = location,
            title = title,
            description = description
        )
    }
}