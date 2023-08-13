package app.meetacy.feature.meetings.database.integration.types

import app.meetacy.feature.meetings.database.integration.meetings.get.DatabaseGetMeetingsViewsMeetingsProvider
import app.meetacy.feature.meetings.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingView
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingsViewsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetMeetingsViewsRepository(private val db: Database) : GetMeetingsViewsRepository {
    override suspend fun getMeetingsViewsOrNull(viewerId: UserId, meetingIds: List<MeetingId>): List<MeetingView?> =
        GetMeetingsViewsUsecase(
            viewMeetingsRepository = DatabaseGetMeetingsViewsViewMeetingsRepository(db),
            meetingsProvider = DatabaseGetMeetingsViewsMeetingsProvider(db)
        ).getMeetingsViewsOrNull(viewerId, meetingIds)
}