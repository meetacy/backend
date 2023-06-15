package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId

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

suspend fun GetMeetingsViewsRepository.getMeetingsViewsOrNull(
    viewerId: UserId,
    meetingIdentities: List<MeetingIdentity>,
): List<MeetingView?> = getMeetingsViewsOrNull(
    viewerId = viewerId,
    meetingIds = meetingIdentities.map { meetingIdentity -> meetingIdentity.id }
).zip(meetingIdentities).map { (meeting, identity) ->
    meeting ?: return@map null
    if (meeting.identity != identity) return@map null
    meeting
}

suspend fun GetMeetingsViewsRepository.getMeetingViewOrNull(
    viewerId: UserId,
    meetingId: MeetingIdentity
): MeetingView? = getMeetingsViewsOrNull(viewerId, listOf(meetingId)).first()

suspend fun GetMeetingsViewsRepository.getMeetingViewOrNull(
    viewerId: UserId,
    meetingId: MeetingId
): MeetingView? = getMeetingsViewsOrNull(viewerId, listOf(meetingId)).first()

suspend fun GetMeetingsViewsRepository.getMeetingView(
    viewerId: UserId,
    meetingId: MeetingIdentity
): MeetingView = getMeetingView(viewerId, meetingId.id)

suspend fun GetMeetingsViewsRepository.getMeetingView(
    viewerId: UserId,
    meetingId: MeetingId
): MeetingView = getMeetingsViews(viewerId, listOf(meetingId)).first()
