package app.meetacy.backend.database.types

import app.meetacy.backend.types.datetime.DateOrTime
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId

class DatabaseMeeting(
    val identity: MeetingIdentity,
    val creatorId: UserId,
    val date: DateOrTime,
    val location: Location,
    val title: String?,
    val description: String?,
    val avatarIdentity: FileIdentity?,
    val visibility: Visibility
) {
    val id: MeetingId = identity.meetingId

    enum class Visibility {
        Public, Private
    }
}
