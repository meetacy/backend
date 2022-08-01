package app.meetacy.backend.test

import app.meetacy.backend.endpoint.meetings.get.GetMeetingResult
import app.meetacy.backend.endpoint.meetings.get.GetParam
import app.meetacy.backend.endpoint.meetings.get.MeetingRepository

object TestMeetingRepository : MeetingRepository {
    override fun getMeeting(getParam: GetParam): GetMeetingResult =
        GetMeetingResult.MeetingNotFound
}
