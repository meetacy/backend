package app.meetacy.backend.database.integration.meetings.get

import app.meetacy.backend.database.meetings.MeetingsStorage
import app.meetacy.backend.database.integration.meetings.participate.DatabaseViewMeetingsUsecaseStorage
import app.meetacy.backend.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.integration.users.get.DatabaseGetUsersViewsRepository
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.get.GetMeetingsViewsUsecase
import app.meetacy.backend.usecase.meetings.get.ViewMeetingsUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView
import app.meetacy.backend.usecase.types.ViewMeetingsRepository
import org.jetbrains.exposed.sql.Database

class DatabaseGetMeetingsViewsViewMeetingsRepository(private val db: Database) : ViewMeetingsRepository {
    override suspend fun viewMeetings(viewerId: UserId, meetings: List<FullMeeting>): List<MeetingView> =
        ViewMeetingsUsecase(DatabaseGetUsersViewsRepository(db), DatabaseFilesRepository(db), DatabaseViewMeetingsUsecaseStorage(db))
            .viewMeetings(viewerId, meetings)
}

class DatabaseGetMeetingsViewsMeetingsProvider(db: Database) : GetMeetingsViewsUsecase.MeetingsProvider {
    private val meetingsStorage = MeetingsStorage(db)
    override suspend fun getMeetings(meetingIds: List<MeetingId>): List<FullMeeting?> =
        meetingsStorage.getMeetingsOrNull(meetingIds)
            .map { meeting -> meeting?.mapToUsecase() }
}
