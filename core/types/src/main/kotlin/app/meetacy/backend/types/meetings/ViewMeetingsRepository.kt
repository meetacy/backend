package app.meetacy.backend.types.meetings

import app.meetacy.backend.types.users.UserId

fun interface ViewMeetingsRepository {
    suspend fun viewMeetings(viewerId: UserId, meetings: List<FullMeeting>): List<MeetingView>
}

suspend fun ViewMeetingsRepository.viewMeeting(
    viewerId: UserId,
    meeting: FullMeeting
): MeetingView = viewMeetings(viewerId, listOf(meeting)).first()
