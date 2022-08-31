package app.meetacy.backend.database.integration.meetings.create

import app.meetacy.backend.database.integration.meetings.participate.DatabaseViewMeetingsUsecaseStorage
import app.meetacy.backend.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.Date
import app.meetacy.backend.types.Location
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.meetings.CreateMeetingUsecase
import app.meetacy.backend.usecase.meetings.ViewMeetingsUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView
import org.jetbrains.exposed.sql.Database

class DatabaseCreateMeetingStorage(private val db: Database) : CreateMeetingUsecase.Storage {
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
            identity = MeetingIdentity(meetingId, accessHash),
            creatorId = creatorId,
            date = date,
            location = location,
            title = title,
            description = description
        )
    }
}

class DatabaseCreateMeetingViewMeetingRepository(private val db: Database) : CreateMeetingUsecase.ViewMeetingRepository {
    override suspend fun viewMeeting(
        viewer: UserId,
        meeting: FullMeeting
    ): MeetingView = ViewMeetingsUsecase(DatabaseGetUsersViewsRepository(db), DatabaseViewMeetingsUsecaseStorage(db))
        .viewMeetings(viewer, meetings = listOf(meeting)).first()
}
