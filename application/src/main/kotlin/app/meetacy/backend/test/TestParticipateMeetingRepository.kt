package app.meetacy.backend.test

import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingResult
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId

object TestParticipateMeetingRepository : ParticipateMeetingRepository {

    override suspend fun participateMeeting(
        meetingId: MeetingId,
        meetingAccessHash: AccessHash,
        accessToken: AccessToken
    ): ParticipateMeetingResult = ParticipateMeetingResult.Success
}
