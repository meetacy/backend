package app.meetacy.backend.mock.storage

import app.meetacy.backend.domain.Location
import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.usecase.types.FullUser

class MockMeeting(
    val id: MeetingId,
    val accessHash: String,
    val creator: FullUser,
    val date: String,
    val location: Location,
    val title: String?,
    val description: String?
)