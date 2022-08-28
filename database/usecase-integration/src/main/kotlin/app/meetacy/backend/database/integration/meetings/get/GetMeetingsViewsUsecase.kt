package app.meetacy.backend.database.integration.meetings.get

import app.meetacy.backend.database.integration.meetings.participate.DatabaseViewMeetingsUsecaseStorage
import app.meetacy.backend.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.types.DatabaseMeeting
import app.meetacy.backend.types.*
import app.meetacy.backend.usecase.meetings.GetMeetingsViewsUsecase
import app.meetacy.backend.usecase.meetings.ViewMeetingsUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView
import org.jetbrains.exposed.sql.Database

class DatabaseGetMeetingsViewsViewMeetingsRepository(private val db: Database) : GetMeetingsViewsUsecase.ViewMeetingsRepository {
    override suspend fun viewMeetings(viewerId: UserId, meetings: List<FullMeeting>): List<MeetingView> =
        ViewMeetingsUsecase(DatabaseGetUsersViewsRepository(db), DatabaseViewMeetingsUsecaseStorage(db))
            .viewMeetings(viewerId, meetings)
}

class DatabaseGetMeetingsViewsMeetingsProvider(db: Database) : GetMeetingsViewsUsecase.MeetingsProvider {
    private val meetingsTable = MeetingsTable(db)
    override suspend fun getMeetings(meetingIds: List<MeetingId>): List<FullMeeting?> =
        meetingsTable.getMeetingsOrNull(meetingIds)
            .map { meeting -> meeting?.mapToUsecase() }
}
