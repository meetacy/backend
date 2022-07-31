package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.usecase.types.GetMeetingsViewsRepository
import app.meetacy.backend.usecase.types.MeetingView

object MockGetMeetingsViewsRepository : GetMeetingsViewsRepository {
    override suspend fun getMeetingsViewsOrNull(viewerId: UserId, meetingIds: List<MeetingId>): List<MeetingView?> =
        mockGetMeetingsViewsUsecase().getMeetingsViewsOrNull(viewerId, meetingIds)
}
