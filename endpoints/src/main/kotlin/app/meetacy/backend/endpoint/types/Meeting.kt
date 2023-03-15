package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.DateSerializable
import app.meetacy.backend.types.serialization.FileIdentitySerializable
import app.meetacy.backend.types.serialization.LocationSerializable
import app.meetacy.backend.types.serialization.MeetingIdentitySerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Status {
    @SerialName("active")
    Active,
    Finished
}

@Serializable
data class Meeting(
    val identity: MeetingIdentitySerializable,
    val creator: User,
    val date: DateSerializable,
    val location: LocationSerializable,
    val title: String?,
    val description: String?,
    val participantsCount: Int,
    val isParticipating: Boolean,
    val avatarIdentity: FileIdentitySerializable?,
    val status: Status
)
