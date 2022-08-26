package app.meetacy.backend.database.types

import app.meetacy.backend.types.*

class DatabaseMeeting(
    val id: MeetingId,
    val accessHash: AccessHash,
    val creatorId: UserId,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?
)
