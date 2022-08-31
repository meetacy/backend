package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.*

data class FullMeeting(
    val identity: MeetingIdentity,
    val creatorId: UserId,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?
) {
    val id: MeetingId = identity.meetingId
}

data class MeetingView(
    val identity: MeetingIdentity,
    val creator: UserView,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?,
    val participantsCount: Int,
    val isParticipating: Boolean
) {
    val id: MeetingId = identity.meetingId
}
