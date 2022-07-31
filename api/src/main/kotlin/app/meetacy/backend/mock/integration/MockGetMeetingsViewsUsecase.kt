package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.mock.storage.MeetingsStorage
import app.meetacy.backend.usecase.meetings.GetMeetingsViewsUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView

private object MockViewMeetingsRepositoryIntegration : GetMeetingsViewsUsecase.ViewMeetingsRepository {
    override suspend fun viewMeetings(viewerId: UserId, meetings: List<FullMeeting>): List<MeetingView> =
        mockViewMeetingsUsecase().viewMeetings(viewerId, meetings)
}

private object MockGetMeetingsViewsUsecaseMeetingsProvider : GetMeetingsViewsUsecase.MeetingsProvider {
    override suspend fun getMeetings(meetingIds: List<MeetingId>): List<FullMeeting?> =
        meetingIds.map { meetingId ->
            val meeting = MeetingsStorage.getMeetingOrNull(meetingId) ?: return@map null
            return@map with (meeting) {
                FullMeeting(id, accessHash, creatorId, date, location, title, description)
            }
        }
}

fun mockGetMeetingsViewsUsecase() = GetMeetingsViewsUsecase(
    MockViewMeetingsRepositoryIntegration,
    MockGetMeetingsViewsUsecaseMeetingsProvider
)
