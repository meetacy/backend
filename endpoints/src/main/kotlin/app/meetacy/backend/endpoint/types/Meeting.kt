package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.datetime.DateSerializable
import app.meetacy.backend.types.serialization.file.FileIdentitySerializable
import app.meetacy.backend.types.serialization.location.LocationSerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdentitySerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val id: MeetingIdentitySerializable,
    val creator: User,
    val date: DateSerializable?,
    val location: LocationSerializable,
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
