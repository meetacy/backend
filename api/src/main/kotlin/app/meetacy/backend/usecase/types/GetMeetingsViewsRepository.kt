package app.meetacy.backend.usecase.types

import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.UserId

interface GetMeetingsViewsRepository {
    suspend fun getMeetingsViewsOrNull(fromUserId: UserId, meetingIds: List<MeetingId>): List<MeetingView?>
}

suspend fun GetMeetingsViewsRepository.getMeetingsViews(fromUserId: UserId, meetingIds: List<MeetingId>): List<MeetingView> =
    getMeetingsViewsOrNull(fromUserId, meetingIds)
        .filterNotNull()
        .apply {
            require(size == meetingIds.size) {
                "Cannot find every meeting ($meetingIds). If it is a normal case, please consider to use getMeetingsViewsOrNull"
            }
        }
