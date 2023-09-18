package app.meetacy.backend.feature.meetings.usecase.integration.get

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingsViewsUsecase
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.ViewMeetingsRepository
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.getMeetingViewsUsecase() {
    val getMeetingUsecase by singleton<GetMeetingsViewsUsecase> {
        val viewMeetingsRepository: ViewMeetingsRepository by getting
        val meetingsStorage: MeetingsStorage by getting
        val meetingsProvider = object : GetMeetingsViewsUsecase.MeetingsProvider {
            override suspend fun getMeetings(meetingIds: List<MeetingId>): List<FullMeeting?> =
                meetingsStorage.getMeetingsOrNull(meetingIds)
        }

        GetMeetingsViewsUsecase(viewMeetingsRepository, meetingsProvider)
    }
}