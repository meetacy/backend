package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId

interface GetMeetingsViewsRepository {
    suspend fun getMeetingsViewsOrNull(viewerId: UserId, meetingIds: List<MeetingId>): List<MeetingView?>
}

suspend fun GetMeetingsViewsRepository.getMeetingsViews(viewerId: UserId, meetingIds: List<MeetingId>): List<MeetingView> =
    getMeetingsViewsOrNull(viewerId, meetingIds)
        .filterNotNull()
        .apply {
            require(size == meetingIds.size) {
                "Cannot find every meeting ($meetingIds). If it is a normal case, please consider to use getMeetingsViewsOrNull"
            }
        }
