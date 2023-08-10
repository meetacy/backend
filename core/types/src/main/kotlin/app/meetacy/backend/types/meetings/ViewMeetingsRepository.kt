package app.meetacy.backend.types.meetings

import app.meetacy.backend.types.users.UserId

interface ViewMeetingsRepository {
    suspend fun viewMeetings(viewerId: UserId, meetings: List<FullMeeting>): List<MeetingView>
}
