package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.*

class MockMeeting(
    val id: MeetingId,
    val accessHash: AccessHash,
    val creatorId: UserId,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?
)