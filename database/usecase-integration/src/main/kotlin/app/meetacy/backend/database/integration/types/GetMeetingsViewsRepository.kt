package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsMeetingsProvider
import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.get.GetMeetingsViewsUsecase
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.MeetingView
import org.jetbrains.exposed.sql.Database

class DatabaseGetMeetingsViewsRepository(private val db: Database) : GetMeetingsViewsRepository {
    override suspend fun getMeetingsViewsOrNull(viewerId: UserId, idMeetings: List<IdMeeting>): List<MeetingView?> =
        GetMeetingsViewsUsecase(
            viewMeetingsRepository = DatabaseGetMeetingsViewsViewMeetingsRepository(db),
            meetingsProvider = DatabaseGetMeetingsViewsMeetingsProvider(db)
        ).getMeetingsViewsOrNull(viewerId, idMeetings)
}