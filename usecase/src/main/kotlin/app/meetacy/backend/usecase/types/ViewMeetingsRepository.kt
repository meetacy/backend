package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.user.UserId

interface ViewMeetingsRepository {
    suspend fun viewMeetings(viewerId: UserId, meetings: List<FullMeeting>): List<MeetingView>
}
