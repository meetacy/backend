package app.meetacy.backend.types.serializable.meetings

import app.meetacy.backend.types.serializable.datetime.Date
import app.meetacy.backend.types.serializable.file.FileId
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.users.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val id: MeetingId,
    val creator: User,
    val date: Date?,
    val location: Location,
    val title: MeetingTitle,
    val description: MeetingDescription?,
    val participantsCount: Int,
    val previewParticipants: List<User>,
    val isParticipating: Boolean,
    val avatarId: FileId?,
    val visibility: Visibility
) {
    @Serializable
    enum class Visibility {
        @SerialName("public")
        Public,
        @SerialName("private")
        Private
    }
}
