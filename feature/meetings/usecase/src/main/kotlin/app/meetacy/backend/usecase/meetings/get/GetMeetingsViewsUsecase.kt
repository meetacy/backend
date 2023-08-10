package app.meetacy.backend.usecase.meetings.get

import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView
import app.meetacy.backend.usecase.types.ViewMeetingsRepository

class GetMeetingsViewsUsecase(
    private val viewMeetingsRepository: ViewMeetingsRepository,
    private val meetingsProvider: MeetingsProvider
) {

    suspend fun getMeetingsViewsOrNull(viewerId: UserId, meetingIds: List<MeetingId>): List<MeetingView?> {
        val meetings = meetingsProvider
            .getMeetings(meetingIds)

        val meetingViews = viewMeetingsRepository
            .viewMeetings(viewerId, meetings.filterNotNull())
            .iterator()

        return meetings.map { meeting ->
            when (meeting) {
                null -> null
                else -> meetingViews.next()
            }
        }
    }

    interface MeetingsProvider {
        suspend fun getMeetings(meetingIds: List<MeetingId>): List<FullMeeting?>
    }
}
