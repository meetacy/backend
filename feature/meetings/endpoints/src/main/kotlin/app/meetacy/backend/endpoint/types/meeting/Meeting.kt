package app.meetacy.backend.endpoint.types.meeting

import app.meetacy.backend.endpoint.types.user.User
import app.meetacy.backend.types.serializable.datetime.Date
import app.meetacy.backend.types.serializable.location.Location
import app.meetacy.backend.types.serialization.file.FileIdentitySerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdentitySerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val id: MeetingIdentitySerializable,
    val creator: User,
    val date: Date?,
    val location: Location,
    val title: String?,
    val description: String?,
    val participantsCount: Int,
    val previewParticipants: List<User>,
    val isParticipating: Boolean,
    val avatarIdentity: FileIdentitySerializable?,
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
