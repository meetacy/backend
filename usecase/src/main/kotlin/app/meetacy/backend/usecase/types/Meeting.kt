package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.*

data class FullMeeting(
    val id: MeetingId,
    val accessHash: AccessHash,
    val creatorId: UserId,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?
)

data class MeetingView(
    val id: MeetingId,
    val accessHash: AccessHash,
    val creator: UserView,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?,
    val participantsCount: Int,
    val isParticipating: Boolean
)
