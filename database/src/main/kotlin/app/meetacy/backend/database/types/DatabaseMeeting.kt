package app.meetacy.backend.database.types

import app.meetacy.backend.types.*

class DatabaseMeeting(
    val identity: MeetingIdentity,
    val creatorId: UserId,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?,
    val avatarIdentity: FileIdentity? = null
) {
    val id: MeetingId = identity.meetingId
}
