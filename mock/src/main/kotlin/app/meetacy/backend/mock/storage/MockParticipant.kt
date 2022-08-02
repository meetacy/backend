package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId

class MockParticipant(
    val meetingId: MeetingId,
    val userId: UserId
)
