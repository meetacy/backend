package app.meetacy.backend.integration.test

import app.meetacy.backend.endpoint.meetings.get.GetMeetingResult
import app.meetacy.backend.endpoint.meetings.get.GetParam
import app.meetacy.backend.endpoint.meetings.get.MeetingProvider

object TestMeetingProvider : MeetingProvider {
    override fun getMeeting(getParam: GetParam): GetMeetingResult =
        GetMeetingResult.MeetingNotFound
}
