package app.meetacy.backend.types.serializable.meetings

import app.meetacy.backend.types.serializable.datetime.Date
import app.meetacy.backend.types.serializable.file.FileIdentity
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serializable.users.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val id: MeetingIdentity,
    val creator: User,
    val date: Date?,
    val location: Location,
    val title: String?,
    val description: String?,
    val participantsCount: Int,
    val previewParticipants: List<User>,
    val isParticipating: Boolean,
    val avatarIdentity: FileIdentity?,
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
