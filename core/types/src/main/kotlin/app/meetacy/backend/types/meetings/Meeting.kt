package app.meetacy.backend.types.meetings

import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FileIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserView

data class FullMeeting(
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

data class MeetingView(
    val identity: MeetingIdentity,
    val creator: UserView,
    val date: Date,
    val location: Location,
    val title: String?,
    val description: String?,
    val participantsCount: Int,
    val previewParticipants: List<UserView>,
    val isParticipating: Boolean,
    val avatarIdentity: FileIdentity? = null,
    val visibility: Visibility
) {
    val id: MeetingId = identity.id

    enum class Visibility {
        Public, Private
    }
}
