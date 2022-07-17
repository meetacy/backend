package app.meetacy.backend.integration.test

import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingResult
import app.meetacy.backend.endpoint.meetings.participate.ParticipateParam

object TestParticipateMeetingRepository : ParticipateMeetingRepository {
    override fun participateMeet(participateParam: ParticipateParam): ParticipateMeetingResult =
        ParticipateMeetingResult.MeetingNotFound
}
