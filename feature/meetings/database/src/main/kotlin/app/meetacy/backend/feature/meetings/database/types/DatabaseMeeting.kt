package app.meetacy.backend.feature.meetings.database.types

import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.MeetingIdentity
import app.meetacy.backend.types.users.UserId

class DatabaseMeeting(
    val identity: MeetingIdentity,
    val creatorId: UserId,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?,
    val avatarId: FileId?,
    val visibility: Visibility
) {
    val id: MeetingId = identity.id

    enum class Visibility {
        Public, Private
    }
}
