package app.meetacy.backend.usecase.meetings.get

import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView
import app.meetacy.backend.usecase.types.ViewMeetingsRepository

class GetMeetingsViewsUsecase(
    private val viewMeetingsRepository: ViewMeetingsRepository,
    private val meetingsProvider: MeetingsProvider
) {

    suspend fun getMeetingsViewsOrNull(viewerId: UserId, idMeetings: List<IdMeeting>): List<MeetingView?> {
        val meetings = meetingsProvider
            .getMeetings(idMeetings)

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
        suspend fun getMeetings(idMeetings: List<IdMeeting>): List<FullMeeting?>
    }
}