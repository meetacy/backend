package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.*

class MockMeeting(
    val identity: MeetingIdentity,
    val creatorId: UserId,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?
) {
    val id: MeetingId = identity.meetingId
}
