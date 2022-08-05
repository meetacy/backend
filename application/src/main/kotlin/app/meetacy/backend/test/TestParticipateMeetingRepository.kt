package app.meetacy.backend.test

import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingResult
import app.meetacy.backend.endpoint.meetings.participate.ParticipateParam
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId

object TestParticipateMeetingRepository : ParticipateMeetingRepository {
    override fun participateMeeting(
        meetingId: MeetingId,
        meetingAccessHash: String,
        accessToken: AccessToken
    ): ParticipateMeetingResult = ParticipateMeetingResult.Success
}
