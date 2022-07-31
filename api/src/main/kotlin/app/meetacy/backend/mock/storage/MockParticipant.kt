package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.UserId

class MockParticipant(
    val meetingId: MeetingId,
    val userId: UserId
)
