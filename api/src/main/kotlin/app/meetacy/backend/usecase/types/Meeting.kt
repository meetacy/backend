package app.meetacy.backend.usecase.types

import app.meetacy.backend.domain.Location
import app.meetacy.backend.domain.MeetingId

data class FullMeeting(
    val id: MeetingId,
    val accessHash: String,
    val creator: FullUser,
    val date: String,
    val location: Location,
    val title: String?,
    val description: String?
)

data class MeetingView(
    val id: MeetingId,
    val accessHash: String,
    val creator: FullUser,
    val date: String,
    val location: Location,
    val title: String?,
    val description: String?
)
