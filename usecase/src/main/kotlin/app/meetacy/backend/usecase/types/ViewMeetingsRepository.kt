package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.user.UserId

interface ViewMeetingsRepository {
    suspend fun viewMeetings(viewerId: UserId, avatarAccessHashList: List<AccessHash?>, meetings: List<FullMeeting>): List<MeetingView>
}
