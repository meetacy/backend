package app.meetacy.backend.usecase.meetings.get

import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.FilesRepository
import app.meetacy.backend.usecase.types.FullMeeting
import app.meetacy.backend.usecase.types.MeetingView
import app.meetacy.backend.usecase.types.ViewMeetingsRepository

class GetMeetingsViewsUsecase(
    private val viewMeetingsRepository: ViewMeetingsRepository,
    private val meetingsProvider: MeetingsProvider,
    private val filesRepository: FilesRepository
) {

    suspend fun getMeetingsViewsOrNull(viewerId: UserId, meetingIds: List<MeetingId>): List<MeetingView?> {
        val meetings = meetingsProvider
            .getMeetings(meetingIds)

        val avatarAccessHashList = filesRepository.getFileIdentityList(meetings.map { it?.avatarId }).map { it?.accessHash }

        val meetingViews = viewMeetingsRepository
            .viewMeetings(viewerId, avatarAccessHashList, meetings.filterNotNull())
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