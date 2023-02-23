package app.meetacy.backend.usecase.types

import app.meetacy.backend.types.*

enum class Status {
    Active, Finished
}

data class FullMeeting(
    val identity: MeetingIdentity,
    val creatorId: UserId,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?,
    val avatarIdentity: FileIdentity?,
    val status: Status? = null
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
    val isParticipating: Boolean,
    val avatarIdentity: FileIdentity?,
    val status: Status? = null
) {
    val id: MeetingId = identity.meetingId
}
