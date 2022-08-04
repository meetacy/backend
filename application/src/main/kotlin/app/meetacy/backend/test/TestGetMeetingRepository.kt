package app.meetacy.backend.test

import app.meetacy.backend.endpoint.meetings.get.GetMeetingResult
import app.meetacy.backend.endpoint.meetings.get.GetMeetingRepository
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId

object TestGetMeetingRepository : GetMeetingRepository {
    override suspend fun getMeeting(
        accessToken: AccessToken,
        meetingId: MeetingId,
        meetingAccessHash: AccessHash
    ): GetMeetingResult =
        GetMeetingResult.MeetingNotFound

}
