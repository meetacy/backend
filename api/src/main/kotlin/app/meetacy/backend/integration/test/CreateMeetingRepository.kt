package app.meetacy.backend.integration.test

import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.endpoint.meetings.create.CreateMeetingResult
import app.meetacy.backend.endpoint.meetings.create.CreateParam

object TestCreateMeetingRepository : CreateMeetingRepository {
    override fun createMeet(createParam: CreateParam): CreateMeetingResult =
        CreateMeetingResult.Success
}
