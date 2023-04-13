package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.types.user.UserId

interface GetMeetingsViewsRepository {
    suspend fun getMeetingsViewsOrNull(viewerId: UserId, idMeetings: List<IdMeeting>): List<MeetingView?>
}

suspend fun GetMeetingsViewsRepository.getMeetingsViews(viewerId: UserId, idMeetings: List<IdMeeting>): List<MeetingView> =
    getMeetingsViewsOrNull(viewerId, idMeetings)
        .filterNotNull()
        .apply {
            require(size == idMeetings.size) {
                "Cannot find every meeting ($idMeetings). If it is a normal case, please consider to use getMeetingsViewsOrNull"
            }
        }
