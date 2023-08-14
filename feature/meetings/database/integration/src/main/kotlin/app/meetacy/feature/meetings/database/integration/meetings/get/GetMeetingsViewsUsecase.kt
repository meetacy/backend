package app.meetacy.feature.meetings.database.integration.meetings.get

import app.meetacy.feature.meetings.database.integration.meetings.participate.DatabaseViewMeetingsUsecaseStorage
import app.meetacy.backend.feature.files.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.feature.users.database.integration.types.DatabaseGetUsersViewsRepository
import app.meetacy.feature.meetings.database.integration.types.mapToUsecase
import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.meetings.ViewMeetingsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingsViewsUsecase
import app.meetacy.backend.feature.meetings.usecase.get.ViewMeetingsUsecase
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
