package app.meetacy.backend.mock.integration.types

import app.meetacy.backend.mock.integration.MockGetMeetingsViewsMeetingsProvider
import app.meetacy.backend.mock.integration.MockGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.meetings.GetMeetingsViewsUsecase
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.MeetingView

object MockGetMeetingsViewsRepository : GetMeetingsViewsRepository {
    override suspend fun getMeetingsViewsOrNull(viewerId: UserId, meetingIds: List<MeetingId>): List<MeetingView?> =
        GetMeetingsViewsUsecase(
            viewMeetingsRepository = MockGetMeetingsViewsViewMeetingsRepository,
            meetingsProvider = MockGetMeetingsViewsMeetingsProvider
        ).getMeetingsViewsOrNull(viewerId, meetingIds)
}
