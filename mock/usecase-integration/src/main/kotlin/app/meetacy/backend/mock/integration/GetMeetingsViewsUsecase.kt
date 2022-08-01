package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.integration.types.MockGetUsersViewsRepository
import app.meetacy.backend.mock.integration.types.mapToUsecase
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.mock.storage.MeetingsStorage
import app.meetacy.backend.usecase.meetings.GetMeetingsViewsUsecase
import app.meetacy.backend.usecase.meetings.ViewMeetingsUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView

object MockGetMeetingsViewsViewMeetingsRepository : GetMeetingsViewsUsecase.ViewMeetingsRepository {
    override suspend fun viewMeetings(viewerId: UserId, meetings: List<FullMeeting>): List<MeetingView> =
        ViewMeetingsUsecase(MockGetUsersViewsRepository, MockViewMeetingsUsecaseStorage)
            .viewMeetings(viewerId, meetings)
}

object MockGetMeetingsViewsMeetingsProvider : GetMeetingsViewsUsecase.MeetingsProvider {
    override suspend fun getMeetings(meetingIds: List<MeetingId>): List<FullMeeting?> =
        meetingIds.map { meetingId ->
            MeetingsStorage
                .getMeetingOrNull(meetingId)
                ?.mapToUsecase()
        }
}
